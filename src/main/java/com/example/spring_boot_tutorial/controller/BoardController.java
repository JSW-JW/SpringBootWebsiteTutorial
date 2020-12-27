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
    public String list(Model model, @PageableDefault(size = 3) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<BoardDto> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
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
                BoardDto boardDto = boardService.changeEntity(board);
                model.addAttribute("board", boardDto);
            }
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid BoardDto boardDto, MultipartFile file, BindingResult bindingResult) throws Exception {
//        System.out.println(boardValidator);
//        boardValidator.validate(board, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "board/form";
//        }
        String imgPath = s3Service.upload(file);
        boardDto.setFilePath(imgPath);

        boardService.saveBoard(boardDto);

        return "redirect:/board/list";
    }


}
