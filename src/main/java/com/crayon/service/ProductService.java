package com.crayon.service;

import com.crayon.dto.PageBean;
import com.crayon.dto.ProductDetail;
import com.crayon.dto.ProductSimple;
import com.crayon.dto.Result;
import com.crayon.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> listProductsByName(String proName);
    List<Product> listProductsByProductionBatch(String productionBatch);

    /**
     * 根据产品获取产品简要信息
     * @param product
     * @return
     */
    ProductSimple getProductSimpleByProduct(Product product);


    /**
     * 根据产品Id获取产品详细信息
     * @param proId
     * @return
     */
    ProductDetail getProductDetailById(Integer proId);


    /**
     * 分页查询
     * @param pageSize
     * @param currentPage
     * @return
     * @throws Exception
     */
    PageBean<ProductSimple> listProductSimplesByPage(int currentPage, int pageSize);


    /**
     * 分页查询商品详细信息
     * @param pageSize
     * @param currentPage
     * @return
     * @throws Exception
     */
    PageBean<ProductDetail> listProductDetailsByPage(int currentPage, int pageSize);


    /**
     * 根据商品id获取销量
     * @param proId
     * @param ordStatus 订单状态筛
     * @return
     * @throws Exception
     */
    Integer getProductSales(int proId, int ordStatus);


}
