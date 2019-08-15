package com.zebra.common.core.controller;

/*
 * 文件名：MyHttpMessageConverter.java 版权：Copyright by http://www.bjleisen.com/ 描述： 修改人：zhangziqi
 * 修改时间：2017年8月25日 跟踪单号： 修改单号： 修改内容：
 */



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.support.config.FastJsonConfig;


public class MyHttpMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object>
{
    /**
     * with fastJson config
     */
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    public MyHttpMessageConverter()
    {
        super(MediaType.ALL);
    }

    /*
     * @see
     * org.springframework.http.converter.GenericHttpMessageConverter#canRead(java.lang.reflect.
     * Type, java.lang.Class, org.springframework.http.MediaType)
     */
    @Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType)
    {
        return super.canRead(contextClass, mediaType);
    }

    /*
     * @see
     * org.springframework.http.converter.GenericHttpMessageConverter#canWrite(java.lang.reflect.
     * Type, java.lang.Class, org.springframework.http.MediaType)
     */
    @Override
	public boolean canWrite(Type type, Class<?> contextClass, MediaType mediaType)
    {
        return super.canWrite(contextClass, mediaType);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException
    {

        InputStream in = inputMessage.getBody();
        return com.alibaba.fastjson.JSON.parseObject(in, fastJsonConfig.getCharset(), type, fastJsonConfig.getFeatures());
    }

    @Override
    public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException
    {
        HttpHeaders headers = outputMessage.getHeaders();
        if (headers.getContentType() == null)
        {
            if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype())
            {
                contentType = getDefaultContentType(t);
            }
            if (contentType != null)
            {
                headers.setContentType(contentType);
            }
        }
        if (headers.getContentLength() == -1)
        {
            Long contentLength = getContentLength(t, headers.getContentType());
            if (contentLength != null)
            {
                headers.setContentLength(contentLength);
            }
        }
        writeInternal(t, outputMessage);
        outputMessage.getBody().flush();

    }

    @Override
    protected boolean supports(Class<?> clazz)
    {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException
    {
        InputStream in = inputMessage.getBody();
        return com.alibaba.fastjson.JSON.parseObject(in, fastJsonConfig.getCharset(), clazz, fastJsonConfig.getFeatures());
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException
    {
        HttpHeaders headers = outputMessage.getHeaders();
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();

        boolean writeAsToString = false;
        if (obj != null)
        {
            String className = obj.getClass().getName();
            if ("com.fasterxml.jackson.databind.node.ObjectNode".equals(className))
            {
                writeAsToString = true;
            }
        }
        if (writeAsToString)
        {
            String text = obj.toString();
            OutputStream out = outputMessage.getBody();
            out.write(text.getBytes());
            if (fastJsonConfig.isWriteContentLength())
            {
                headers.setContentLength(text.length());
            }
        }
        else
        {
            int len = com.alibaba.fastjson.JSON.writeJSONString(outnew, //
                fastJsonConfig.getCharset(), //
                obj, //
                fastJsonConfig.getSerializeConfig(), //
                fastJsonConfig.getSerializeFilters(), //
                fastJsonConfig.getDateFormat(), //
                com.alibaba.fastjson.JSON.DEFAULT_GENERATE_FEATURE, //
                fastJsonConfig.getSerializerFeatures());
            if (fastJsonConfig.isWriteContentLength())
            {
                headers.setContentLength(len);
            }

            OutputStream out = outputMessage.getBody();
            outnew.writeTo(out);
        }

        outnew.close();

    }

    public FastJsonConfig getFastJsonConfig()
    {
        return fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig)
    {
        this.fastJsonConfig = fastJsonConfig;
    }

}
