package twentyfourFORseven.publicpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.dto.ImageFileDto;
import twentyfourFORseven.publicpoll.dto.PollDto;
import twentyfourFORseven.publicpoll.service.ImageFileService;
import twentyfourFORseven.publicpoll.service.PollService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/images")
public class ImageController {
    private PollService pollService;
    private ImageFileService imageFileService;

    public ImageController(PollService pollService, ImageFileService imageFileService) {
        this.pollService = pollService;
        this.imageFileService = imageFileService;
    }

    // /image_upload 페이지에 방문하면 해당 html파일을 화면에 뿌려주기
    @GetMapping("/image_upload")
    public String post() {
        return "test_pages/image_upload_test.html";
    }

    @PostMapping("/image_upload")
    public ResponseEntity write(@RequestParam("file") MultipartFile files, Integer poll_id, Integer item_num) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = item_num.toString() + ".jpg";
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "/files/" + poll_id.toString();
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "/" + filename;
            files.transferTo(new File(filePath));

            ImageFileDto fileDto = new ImageFileDto();
            fileDto.setPoll_id(poll_id);
            fileDto.setItem_num(item_num);
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            imageFileService.saveFile(fileDto);
            String resMessage = "이미지 추가 완료";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage), HttpStatus.OK);

        } catch(Exception e) {
            String resMessage = "이미지 추가 실패";
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/images/{poll_id}/{item_num}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("item_num") Integer item_num, @PathVariable("poll_id") Integer poll_id) throws IOException {
        InputStream imageStream = new FileInputStream("/home/ubuntu/public-poll/files/" + poll_id.toString() + "/" + item_num.toString() + ".jpg");
        byte[] imageByteArray = imageStream.readAllBytes();
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }
}
