package com.rc.components.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import lombok.Data;

/**
 * 概率随机工具
 * @author: rc
 * @date: 2018年2月5日 下午4:55:48
 * @version: V1.0
 * @review: rc/2018年2月5日 下午4:55:48
 */
public class ProbRandom {
	
	private final Integer randomBound; //随机边界范围
	
	private final Random random; //随机生成器
	
	private final HashMap<Object, Integer> originalMap; //随机元素的原始数据容器
	
	private ArrayList<ProbDistribution> probDistributions; //元素分布概率的数组
	
	private boolean updateFlag; //原始数据容器是否有更新
	
	private Integer originalSum; //原始数据随机值求和
	
	private Integer zeroOriginalCount; //原始数据随机值为0(未设置随机值)的元素个数
	
	public ProbRandom(Integer randomBound) {
		this.originalMap = new HashMap<>();
		this.updateFlag = true;
		this.randomBound = randomBound;
		this.originalSum = 0;
		this.zeroOriginalCount = 0;
		this.random = new Random();
	}
	
	/**
	 * 生成随机数
	 * @return
	 */
	public Object next(){
		distribute(); //计算元素分布的概率值
		if (null == probDistributions) {
			return null;
		}
		int nextInt = random.nextInt(randomBound);
		ProbDistribution prob = probDistributions.stream().filter(c->(c.floor<=nextInt&&c.ceil>nextInt)).findFirst().get();
		return prob.getKey();
	}
	
	/**
	 * 添加随机元素和概率值，返回添加后的概率值
	 * 如果prob=0 ，概率值计算为总体概率值确定的默认值
	 * @param key
	 * @param prob
	 * @return
	 */
	public void put(Object key, Integer prob){
		//校验
		if (null == key || prob == null ) {
			throw new NullPointerException("key或者prob不能为空");
		}
		if (prob < 0) {
			throw new RuntimeException("prob不能小于0");
		}
		//添加
		Integer previousProb = originalMap.put(key, prob);
		//更新
		if (prob == 0) {
			zeroOriginalCount ++ ;
		} else {
			originalSum += prob;
		}
		if (previousProb == null) {
			updateFlag = true;
		} else {
			if (previousProb == 0) {
				zeroOriginalCount -- ;
			} else {
				originalSum -= previousProb;
			}
			if (prob != previousProb) {
				updateFlag = true;
			}
		}
	}
	
	/**
	 * 刪除已經添加的元素，返回元素設置的隨機值
	 * 如果元素不存在，返回null
	 * @param key
	 * @return
	 */
	public Integer remove(Object key){
		//校验
		if (null == key) {
			throw new NullPointerException("key或者prob不能为空");
		}
		//刪除
		Integer remove = originalMap.remove(key);
		if (null != remove) {
			updateFlag = true;
		}
		return remove;
	}
	
	/**
	 * 等同于put(key,0)
	 * @param key
	 */
	public void put(Object key){
		put(key,0);
	}
	
	/**
	 * 返回随机值边界randomBound
	 * @return
	 */
	public Integer getRandomBound(){
		return randomBound;
	}
	
	/**
	 * 返回元素的随机值prob
	 * @param key
	 * @return
	 */
	public Integer getProb(Object key){
		return originalMap.get(key);
	}
	
	/**
	 * 返回元素的集合
	 * @return
	 */
	public Set<Object> getKeySet(){
		return originalMap.keySet();
	}
		
	//计算概率元素的概率分布
	private void distribute() {
		if (randomBound < originalSum) {
			throw new RuntimeException("总随机值不能大于随机值边界");
		}
		//如果原始数据容器有更新，就从新计算分布概率数组
		if (updateFlag) {
			//创建分布概率数组
			probDistributions = new ArrayList<ProbRandom.ProbDistribution>();
			
			//重组分布概率数组
			Integer tempFloor = 0;
			if (zeroOriginalCount == 0) {
				for (Entry<Object, Integer> e : originalMap.entrySet()) {
					Integer ceil = (int) (tempFloor + e.getValue()*1.00*randomBound/originalSum);
					probDistributions.add(new ProbDistribution(e.getKey(), tempFloor, ceil)) ;
					tempFloor = ceil; 
				}
			} else {
				Integer defProbSize = (int) ((randomBound - originalSum)*1.00/zeroOriginalCount);
				for (Entry<Object, Integer> e : originalMap.entrySet()) {
					Integer ceil;
					if (e.getValue() == 0) {
						ceil = tempFloor + defProbSize;
					} else {
						ceil = (int) (tempFloor + e.getValue());
						
					}
					probDistributions.add(new ProbDistribution(e.getKey(), tempFloor, ceil)) ;
					tempFloor = ceil; 
				}
			}

			updateFlag = false;
		}
	}
	
	
	//描述单个元素分布概率情况
	@Data
	private class ProbDistribution{
		private Object key;
		private Integer floor;
		private Integer ceil;
		ProbDistribution(Object key, Integer floor, Integer ceil){
			this.key = key;
			this.floor = floor;
			this.ceil = ceil;
		}
	}
	
	

}
