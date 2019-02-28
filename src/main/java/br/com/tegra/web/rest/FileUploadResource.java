package br.com.tegra.web.rest;

import br.com.tegra.domain.Airport;
import br.com.tegra.service.AirportService;
import br.com.tegra.web.rest.errors.BadRequestAlertException;
import br.com.tegra.web.rest.util.HeaderUtil;
import br.com.tegra.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Airport.
 */
@RestController
@RequestMapping("/api")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);



    public FileUploadResource() {

    }

    /**
     * POST  /airports : Create a new airport.
     *
     * @param file file to upload
     * @return the ResponseEntity with status 201 (Created) and with body the new airport, or with status 400 (Bad Request) if the airport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fileUpload")
    public String update(@RequestParam("file") MultipartFile file,
                                          RedirectAttributes redirectAttributes) throws URISyntaxException {
        log.debug("REST request to save File : {}", file);
        redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
