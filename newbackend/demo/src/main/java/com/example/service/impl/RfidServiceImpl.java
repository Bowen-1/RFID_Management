package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.VO.PageVO;
import com.example.entity.Rfid;
import com.example.form.RfidForm;
import com.example.form.SearchForm;
import com.example.mapper.RfidMapper;
import com.example.service.RfidService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2024-04-24
 */
@Service
public class RfidServiceImpl extends ServiceImpl<RfidMapper, Rfid> implements RfidService {

    public final String configFile = "src/main/resources/config-example.toml";


    @Autowired
    private RfidMapper rfidMapper;

    @Override
    public Boolean create(RfidForm rfidForm) throws Exception {
        QueryWrapper<Rfid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",rfidForm.getName());
        Rfid rfid = this.rfidMapper.selectOne(queryWrapper);
        if(rfid != null){
            return false;
        }
            else{
                Rfid rfid1 = new Rfid();
                rfid1.setName(rfidForm.getName());
                rfid1.setLocation(rfidForm.getLocation());
                int insert = this.rfidMapper.insert(rfid1);
                if (insert !=1) return false;

                BcosSDK sdk = BcosSDK.build(configFile);
                Client client = sdk.getClient(Integer.valueOf(1));
                CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
                AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor
                        (client, keyPair, "src/main/resources/abi/", "src/main/resources/bin/");
                List<Object> rfid2 = new ArrayList<>();
                rfid2.add(rfidForm.getName());
                rfid2.add(rfidForm.getLocation());
                TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader
                        ("rfid","0xc0c666384f2134324b356f77898css999g23435d","addRfid",rfid2);

                List<Object> returnValues = transactionResponse.getReturnObject();
                if (returnValues != null){
                    for (Object value : returnValues){
                        System.out.println(" " + value.toString());
                    }
                }

            }
  return true;



    }

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Rfid> rfidPage = new Page<>(page,size);
        Page<Rfid> resultPage = this.rfidMapper.selectPage(rfidPage,null);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(resultPage.getRecords());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Rfid> rfidPage = new Page<>(searchForm.getPage(),searchForm.getSize());
        Page<Rfid> resultPage = null;
        if(searchForm.getValue().equals("")){
            resultPage = this.rfidMapper.selectPage(rfidPage,null);
        }else{
            QueryWrapper<Rfid> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = this.rfidMapper.selectPage(rfidPage,queryWrapper);

        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(resultPage.getRecords());
        return pageVO;
    }
}
