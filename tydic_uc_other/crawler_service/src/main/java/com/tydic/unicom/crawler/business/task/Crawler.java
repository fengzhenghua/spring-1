/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.tydic.unicom.crawler.business.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Executor;

public class Crawler {
    public static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
    public Crawler() {
    }
    /**
     * 根据任务管理器和执行器构造爬虫
     *
     * @param dbManager 任务管理器
     * @param executor 执行器
     */
    public Crawler(Executor executor) {
        this.executor = executor;
    }

    protected int status;
    public final static int RUNNING = 1;
    public final static int STOPED = 2;
    protected boolean resumable = false;
    protected int threads = 50;

    protected int topN = -1;
    protected long executeInterval = 0;

    protected CrawlDatums seeds = new CrawlDatums();
    protected CrawlDatums forcedSeeds = new CrawlDatums();
    protected int maxExecuteCount = -1;

    protected Executor executor = null;

    /**
     * 开始爬取，迭代次数为depth
     *
     * @param depth 迭代次数
     * @throws Exception 异常
     */
    public void start(int depth) throws Exception {
        status = RUNNING;
    }

    /**
     * 停止爬虫
     */
    public void stop() {
        status = STOPED;
    }
//    /**
//     * 返回是否断点爬取
//     *
//     * @return 是否断点爬取
//     */
//    public boolean isResumable() {
//        return resumable;
//    }
//
//    /**
//     * 设置是否断点爬取
//     * @param resumable 是否断点爬取
//     */
//    public void setResumable(boolean resumable) {
//        this.resumable = resumable;
//    }

    public int getMaxExecuteCount() {
        return maxExecuteCount;
    }

    /**
     * 设置每个爬取任务的最大执行次数，爬取或解析失败都会导致执行失败。 当一个任务执行失败时，爬虫会在后面的迭代中重新执行该任务，
     * 当该任务执行失败的次数超过最大执行次数时，任务生成器会忽略该任务
     *
     * @param maxExecuteCount 每个爬取任务的最大执行次数
     */
    public void setMaxExecuteCount(int maxExecuteCount) {
        this.maxExecuteCount = maxExecuteCount;
    }

    /**
     * 获取每个爬取任务的最大执行次数
     *
     * @return 每个爬取任务的最大执行次数
     */
    public Executor getExecutor() {
        return executor;
    }

    /**
     * 设置执行器
     *
     * @param executor 执行器
     */
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    /**
     * 获取执行间隔
     *
     * @return 执行间隔
     */
    public long getExecuteInterval() {
        return executeInterval;
    }

    /**
     * 设置执行间隔
     *
     * @param executeInterval 执行间隔
     */
    public void setExecuteInterval(long executeInterval) {
        this.executeInterval = executeInterval;
    }
}
