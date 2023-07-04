package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import storevid.dao.StoreVidFinishDto;
import storevid.dao.StoreVidInfoDto;
import storevid.entity.WaitRequest;
import storevid.repo.WaitRequestRepo;
import storevid.service.SenderService;
import storevid.service.StoreVidService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreVidServiceImpl implements StoreVidService {

    private final WaitRequestRepo waitRequestRepo;
    private final SenderService senderService;

    @Override
    public void receiveFinishUpdate(StoreVidFinishDto finishDto) {
        System.out.println(finishDto.toString());
        Optional<WaitRequest> waitRequestOptional = waitRequestRepo.findByRequestSSID(finishDto.getRequestSsid());
        if (waitRequestOptional.isPresent()){

            WaitRequest waitRequest = waitRequestOptional.get();
            senderService.deleteMessage(waitRequest.getChatId(),waitRequest.getMessageId());
            senderService.forwardMessage(new ForwardMessage(waitRequest.getChatId()+"",finishDto.getSent().getUsername(),finishDto.getSent().getMessageId()));
        }
    }

    @Override
    public void receiveInfoUpdate(StoreVidInfoDto processDto) {
        System.out.println(processDto.toString());
        Optional<WaitRequest> waitRequestOptional = waitRequestRepo.findByRequestSSID(processDto.getRequestSsid());

        if (waitRequestOptional.isPresent()){
            WaitRequest waitRequest = waitRequestOptional.get();

        }
    }
}
