package com.njupt.garbage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.njupt.garbage.common.pojo.EUDataGridResult;
import com.njupt.garbage.common.pojo.Result;
import com.njupt.garbage.common.utils.IDUtils;
import com.njupt.garbage.mapper.GarbageItemMapper;
import com.njupt.garbage.pojo.GarbageItem;
import com.njupt.garbage.pojo.GarbageItemExample;
import com.njupt.garbage.service.GarbageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class GarbageItemServiceImpl implements GarbageItemService {

    @Autowired
    private GarbageItemMapper garbageItemMapper;

    @Transactional
    @Override
    public Result addGarbageItem(GarbageItem garbageItem) {
        //设置id
        Long id = IDUtils.genItemId();
        //设置状态1-正常，2-下架，3-删除',

        //设置创建和更新时间为当前时间
        Date date = new Date();
        garbageItem.setId(id);
        garbageItem.setCreated(date);
        garbageItem.setUpdated(date);
        garbageItem.setItemStatus((byte) 1);
        garbageItemMapper.insert(garbageItem);
        return Result.ok();
    }

    @Override
    public GarbageItem findGarbageItemById(long id) {
        GarbageItem garbageItem = garbageItemMapper.selectByPrimaryKey(id);
        return garbageItem;
    }

    @Override
    public List<GarbageItem> findGarbageByName(String name) {
        GarbageItemExample example = new GarbageItemExample();
        GarbageItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemNameLike(name);
        List<GarbageItem> list = garbageItemMapper.selectByExample(example);
        return list;
    }

    @Override
    public EUDataGridResult findItemList(int page, int rows) throws Exception {
        GarbageItemExample example = new GarbageItemExample();
        // 设置分页
        PageHelper.startPage(page, rows);
        List<GarbageItem> list = garbageItemMapper.selectByExample(example);
        PageInfo<GarbageItem> pageInfo = new PageInfo<GarbageItem>(list);
        EUDataGridResult euDataGridResult = new EUDataGridResult();
        euDataGridResult.setRows(list);
        euDataGridResult.setTotal(pageInfo.getTotal());
        return euDataGridResult;
    }

    @Override
    public Result deleteByItemName(String name) {

        return null;
    }
}
