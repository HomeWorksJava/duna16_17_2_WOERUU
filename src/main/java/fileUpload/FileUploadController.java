package fileUpload;

import fileUpload.Repository.LogRepository;
import fileUpload.Repository.UploadedFileRepository;
import fileUpload.Store.StorageFileNotFoundException;
import fileUpload.Store.StorageService;
import fileUpload.Tables.Log;
import fileUpload.Tables.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    LogRepository logRepository;

    @Autowired
    UploadedFileRepository uploadedFileRepository;

    @GetMapping("/feltoltott_fajlok_listaja")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadedFiles";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {

        storageService.store(file);
        uploadedFileRepository.save(setFilesTable(file));
        logRepository.save(setLog(request,file));
        redirectAttributes.addFlashAttribute("message",
                "Sikeresen feltöltötted a file-t. " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    public UploadedFile setFilesTable(MultipartFile file){
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(file.getOriginalFilename());
        return uploadedFile;
    }

    public Log setLog(HttpServletRequest request, MultipartFile file){
        Log log = new Log();
        String userURL="";
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        log.setFileName(file.getOriginalFilename());
        log.setHost(request.getScheme() + "//" + request.getServerName());

        if (queryString == null) {
            userURL = requestURL.toString();
        } else {
            userURL = requestURL.append('?').append(queryString).toString();
        }
        log.setUrl(userURL);

        return log;
    }

}
