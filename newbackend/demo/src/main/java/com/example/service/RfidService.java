package com.example.service;

import com.example.VO.PageVO;
import com.example.entity.Rfid;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.RfidForm;
import com.example.form.SearchForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2024-04-24
 */
public interface RfidService extends IService<Rfid> {

    public Boolean create(RfidForm rfidForm) throws Exception;
    public PageVO list(Integer page, Integer size);
    public PageVO search(SearchForm searchForm);

}
