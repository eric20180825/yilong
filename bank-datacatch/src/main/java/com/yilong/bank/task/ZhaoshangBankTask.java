package com.yilong.bank.task;

import com.alibaba.fastjson.JSONObject;
import com.yilong.bank.entity.Page;
import com.yilong.bank.entity.ZhaoshangBank;
import com.yilong.bank.service.PageService;
import com.yilong.bank.service.ZhaoshangBankService;
import com.yilong.utils.DateUtil;
import com.yilong.utils.FileUtil;
import com.yilong.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.util.List;

@Component
public class ZhaoshangBankTask {
    @Value("${project.bank.url.zhaoshang}")
    private String url;
    @Value("${project.bank.params.zhaoshang}")
    private String params;

    @Autowired
    private ZhaoshangBankService zhaoshangBankService;
    @Autowired
    private PageService pageService;

    int i = 0;

    @Scheduled(cron = "*/5 * * * * ?")
    public void run() throws Exception {
        i++;
        System.out.println("===========================招商银行第" + i + "次数据抓取任务启动=================================");

        //获取要抓取的页码
        String currentMonth = DateUtil.getCurrentTime("yyyy-MM");
        Page page = pageService.findByCurrentMonth(currentMonth);
        if (page == null || page.getPageIndex() == null) {//初始化数据
            page = new Page();
            page.setPageIndex(1);
            page.setCurrentMonth(currentMonth);
            try {
                page = pageService.save(page);
            }  catch (DataIntegrityViolationException e) {
                System.out.println("分页表惟一主键约束");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Integer pageIndex = page.getPageIndex();
        Integer pageSize = 50;

        String clientNo = "";
        String accountUID = "";
        String subAccountNo = "";

        List<String> list = FileUtil.ReadLineTxtFile("D:\\zhaoshangConfig.txt");
        for (String str : list) {
            if (!StringUtils.isEmpty(str)) {
                String[] result = str.split(":");
                if ("CLIENTNO".equalsIgnoreCase(result[0])) {
                    clientNo = result[1].trim();
                } else if ("ACCOUNTUID".equalsIgnoreCase(result[0])) {
                    accountUID = result[1].trim();
                } else if ("SUBACCOUNTNO".equalsIgnoreCase(result[0])) {
                    subAccountNo = result[1].trim();
                }
            }
        }

        //计算当月第一天和最后一天
        String beginDate = DateUtil.getCurrentMonthFirstDay("yyyyMMdd");
        String endDate = DateUtil.getCurrentMonthLastDay("yyyyMMdd");
        String paramsReplace = params.replace("CLIENTNO", clientNo).replace("ACCOUNTUID", accountUID)
                .replace("SUBACCOUNTNO", subAccountNo).replace("BEGINDATE", beginDate)
                .replace("ENDDATE", endDate).replace("PAGEINDEX", pageIndex + "")
                .replace("PAGESIZE", pageSize + "");
        String result = HttpClientUtil.postParameters(url, paramsReplace);
        System.out.println("result:" + result);

        String contentStr = "";
        try {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String content = jsonObject.getString("content");
            contentStr = URLDecoder.decode(content, "utf-8");
            System.out.println("抓取的content数据为:" + contentStr);
        } catch (Exception e) {
            System.out.println("抓取数据转换格式异常!");
            return;
        }
        int begin = contentStr.indexOf("<tr>");
        if (begin < 0) {
            System.out.println("空白数据");
            return;
        }
        contentStr = contentStr.substring(begin, contentStr.length());
        System.out.println("截取后的数据为:" + contentStr);
        Document document = Jsoup.parse(contentStr);
        Elements elements = document.getElementsByTag("tr");

        if (elements.size() >= 50) {//表示当前页已满，开始抓取下一页
            pageIndex++;
            page.setPageIndex(pageIndex);
            pageService.save(page);
        }

        for (Element element : elements) {
            Elements tds = element.getElementsByTag("td");
            ZhaoshangBank bank = new ZhaoshangBank();
            for (int i = 0; i < 5; i++) {
                String value = tds.get(i).text();
                if (i == 0) {
                    bank.setTradeDate(value);
                } else if (i == 1) {
                    bank.setTradeMoney(value);
                } else if (i == 2) {
                    bank.setBalance(value);
                } else if (i == 3) {
                    bank.setType(value);
                } else if (i == 4) {
                    bank.setRemark(value);
                }
            }

            bank.setAccountUid(accountUID);
            bank.setSubAccountNo(subAccountNo);
            try {
                zhaoshangBankService.save(bank);
            } catch (DataIntegrityViolationException e) {
                System.out.println("惟一主键约束");
                continue;


            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        System.out.println("===========================招商银行第" + i + "次据抓取任务结束=================================");
    }

    public static void main(String[] args) throws Exception {
        String url = "https://cbsz.ebank.cmbchina.com/CmbBank_DebitCard_AccountManager/UI/DebitCard/AccountQuery/Pro8/am_QueryTrans.aspx";
        String parames = "RequestType=AjaxRequest&ClientNo=D3C87659779261D5CD61FDB39A5624B2133775706037652800036828&AccountUID=J3shJkUb81rFiHtrnGP80ohgly6KSY6-&SubAccountNo=128328910420002&TransTypeID=-&TransType=%5B+%E5%85%A8%E9%83%A8+%5D&BeginDate=20190124&EndDate=20190130&IncomeFrom=&IncomeTo=&PayFrom=&PayTo=&Memo=&SortName=DateTime&SortDirection=desc";

        url = url + "?" + parames;
        String result = HttpClientUtil.get(url);

        String urlDecode = URLDecoder.decode(result, "utf-8");
        System.out.println(urlDecode);
    }

}
