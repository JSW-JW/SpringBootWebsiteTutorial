package com.example.spring_boot_tutorial.controller;

import com.example.spring_boot_tutorial.model.Board;
import com.example.spring_boot_tutorial.model.BoardDto;
import com.example.spring_boot_tutorial.repository.BoardRepository;
import com.example.spring_boot_tutorial.service.BoardService;
import com.example.spring_boot_tutorial.service.S3Service;
import com.example.spring_boot_tutorial.validator.BoardValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private S3Service s3Service;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new BoardDto());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            if (board != null) {
                BoardDto boardDto = boardService.toDto(board);
                model.addAttribute("board", boardDto);
            }
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String submitBoard(@Valid BoardDto boardDto, @RequestParam(required = false) Long id, MultipartFile file, BindingResult bindingResult) throws Exception {
//        System.out.println(boardValidator);
//        boardValidator.validate(board, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "board/form";
//        }



        if (!file.isEmpty()) {

            String imgPath = s3Service.upload(file);
            String storedFileName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
            String originalFileName = file.getOriginalFilename();

            boardDto.setFilePath(imgPath);
            boardDto.setOriginalFileName(originalFileName);
            boardDto.setStoredFileName(storedFileName);

            Board board = boardService.toEntity(boardDto, true);
            boardService.saveBoard(board);

        } else {
            Board board = boardService.toEntity(boardDto, false);
            boardService.saveBoard(board);
        }

        return "redirect:/board/list";
    }

    /*  TODO: 1. add s3 KeyName column in the db
              2.
     */

    @PostMapping("/delete") // GET mapping for deleting s3, db object.
    public String deleteBoard(@ModelAttribute BoardDto boardDto) throws Exception {
        s3Service.delete(boardDto.getPicture().getStoredFileName());
        boardRepository.deleteById(boardDto.getId());

        return "redirect:/board/list";
    }


}
