package storevid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import storevid.dao.StoreVidFinishDto;
import storevid.dao.StoreVidInfoDto;
import storevid.service.StoreVidService;

@RestController
@RequestMapping("/storevid")
@RequiredArgsConstructor
public class StVidController {

    private final StoreVidService storeVidService;

    @PostMapping("/finish")
    public void updatesFromServer(@RequestBody StoreVidFinishDto finishDto) {
        storeVidService.receiveFinishUpdate(finishDto);
    }
    @PostMapping("/info")
    public void updatesFromServer(@RequestBody StoreVidInfoDto infoDto) {
        storeVidService.receiveInfoUpdate(infoDto);
    }

}
