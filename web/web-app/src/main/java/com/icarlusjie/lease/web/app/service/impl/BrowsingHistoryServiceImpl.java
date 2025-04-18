package com.icarlusjie.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icarlusjie.lease.model.entity.BrowsingHistory;
import com.icarlusjie.lease.model.entity.UserInfo;
import com.icarlusjie.lease.web.app.mapper.BrowsingHistoryMapper;
import com.icarlusjie.lease.web.app.service.BrowsingHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icarlusjie.lease.web.app.vo.history.HistoryItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Override
    public IPage<HistoryItemVo> pageHistoryItemByUserId(Page<HistoryItemVo> page, Long userId) {
        return browsingHistoryMapper.pageHistoryItemByUserId(page,userId);
    }

    @Override
    @Async //开启异步操作
    public void saveHistory(Long userId, Long roomId) {
        System.out.println("保存浏览历史 -" + Thread.currentThread().getName());

        LambdaQueryWrapper<BrowsingHistory> historyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        historyLambdaQueryWrapper.eq(BrowsingHistory::getId,userId);
        historyLambdaQueryWrapper.eq(BrowsingHistory::getRoomId,roomId);
        BrowsingHistory selectOne = browsingHistoryMapper.selectOne(historyLambdaQueryWrapper);
        if(selectOne != null){
            selectOne.setBrowseTime(new Date());
            browsingHistoryMapper.updateById(selectOne);
        }else {
            BrowsingHistory browsingHistory = new BrowsingHistory();
            browsingHistory.setUserId(userId);
            browsingHistory.setRoomId(roomId);
            browsingHistory.setBrowseTime(new Date());
            browsingHistoryMapper.insert(browsingHistory);
        }

    }
}