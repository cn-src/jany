/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.storage.minio;

import cn.javaer.jany.type.StorableObject;

import java.io.InputStream;

/**
 * The interface Minio service.
 *
 * @author cn -src
 */
public interface MinioService {
    /**
     * 生成预签名 get url.
     *
     * @param objectName the object name
     *
     * @return string
     */
    String presignedGetUrl(String objectName);

    /**
     * 生成预签名 put url.
     *
     * @param objectName the object name
     *
     * @return the string
     */
    String presignedPutUrl(String objectName);

    /**
     * 生成预签名 delete url.
     *
     * @param objectName the object name
     *
     * @return the string
     */
    String presignedDeleteUrl(String objectName);

    /**
     * 它返回指定对象的预签名 URL
     *
     * @param objectName 要预签名的对象的名称。
     *
     * @return 预签名的对象。
     */
    PresignedObject presignedObject(String objectName);

    /**
     * 将输入流的内容上传到具有给定名称的对象。
     *
     * 函数返回一个StorableObject对象，这个对象是一个Java对象，代表刚刚上传的对象
     *
     * @param objectName 要上传的对象的名称。
     * @param in 要上传的文件的输入流。
     *
     * @return 可存储对象
     */
    StorableObject upload(String objectName, InputStream in);

    /**
     * 它将文件上传到服务器，并返回一个可用于将文件存储在数据库中的 StorableObject
     *
     * @param filename 要上传的文件的名称。
     * @param in 要上传的文件的输入流。
     *
     * @return 一个可存储对象。
     */
    StorableObject uploadTmp(String filename, InputStream in);

    /**
     * 将源对象复制到目标对象
     *
     * @param sourceObject 源对象的名称。
     * @param targetObject 目标对象的名称。
     */
    void copy(String sourceObject, String targetObject);

    /**
     * 将源对象移动到目标对象。
     *
     * @param sourceObject 要移动的对象。
     * @param targetObject 要将 sourceObject 移动到的目标对象。
     */
    void move(String sourceObject, String targetObject);

    /**
     * 删除具有给定名称的对象
     *
     * @param objectName 要删除的对象的名称。
     */
    void delete(String objectName);
}