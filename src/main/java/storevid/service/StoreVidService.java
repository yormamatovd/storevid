package storevid.service;

import storevid.dao.StoreVidFinishDto;
import storevid.dao.StoreVidInfoDto;

public interface StoreVidService {
    void receiveFinishUpdate(StoreVidFinishDto finishDto);

    void receiveInfoUpdate(StoreVidInfoDto processDto);
}
