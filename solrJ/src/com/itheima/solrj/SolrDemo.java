package com.itheima.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xie on 2017/6/22.
 */
public class SolrDemo {
    //添加
    @Test
    public void  run1() throws IOException, SolrServerException {
        String url="http://localhost:8080/solr";
        SolrServer solrServer = new HttpSolrServer(url);
        SolrInputDocument doc=new SolrInputDocument();
        doc.setField("id",4);
        doc.setField("name","李四2");
        solrServer.add(doc);
        solrServer.commit();
    }
    //删除
    @Test
    public void  run2() throws IOException, SolrServerException {
        String url="http://localhost:8080/solr";
        SolrServer solrServer = new HttpSolrServer(url);
        solrServer.deleteById("1");
        solrServer.commit();
    }
    //删除全部
    @Test
    public void  run3() throws IOException, SolrServerException {
        String url="http://localhost:8080/solr";
        SolrServer solrServer = new HttpSolrServer(url);
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
    //查询
    @Test
    public void  run4() throws IOException, SolrServerException {
      //创建搜索对象
        SolrQuery solrQuery=new SolrQuery();
        //设置搜索条件
        solrQuery.setQuery("text:李四");
        //发起搜索请求---连接solr服务器
        String baseURL="http://localhost:8080/solr/";
        SolrServer solrServer=new HttpSolrServer(baseURL);
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        System.out.println("总条数是："+numFound);
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            System.out.println(result.get("name"));

        }
    }
    //复杂查询
    @Test
    public void run5() throws SolrServerException {
        //发起搜索请求---连接solr服务器
        String baseURL="http://localhost:8080/solr/";
        SolrServer solrServer=new HttpSolrServer(baseURL);
        //创建搜索对象
        SolrQuery solrQuery=new SolrQuery();
        //设置查询条件
        solrQuery.setQuery("钻石");
        //过滤条件、
        solrQuery.setFilterQueries("product_price:[* TO 20]");
        //添加过滤条件
        solrQuery.addSort("product_price", SolrQuery.ORDER.desc);
        //分页
        solrQuery.setStart(0);
        solrQuery.setRows(5);
        //字段列表
        solrQuery.setFields("id","product_name","product_price");
        //设置默认搜索域
        solrQuery.set("df","product_name");
        //高亮显示
        solrQuery.setHighlight(true);
        //高亮显示的域
        solrQuery.addHighlightField("product_name");
        //高亮显示的前缀
        solrQuery.setHighlightSimplePre("<em>");
        //高亮显示的后缀
        solrQuery.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);

        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            System.out.print(result.get("id"));
            System.out.print(result.get("product_name"));
            System.out.println(result.get("product_price"));
        }

    }

}
