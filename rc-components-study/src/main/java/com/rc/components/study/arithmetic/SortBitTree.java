package com.rc.components.study.arithmetic;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 排序二叉树
 * @author: rc
 * @date: 2018年3月11日 下午12:15:42
 * @version: V1.0
 * @review: rc/2018年3月11日 下午12:15:42
 */
@Data
public class SortBitTree<T extends Comparable<T>> {
	
	private Node root;
	
	public Node add(T t){
		return creatNext(root, t);
	}
	
	private Node creatNext(Node parent, T t){
		if (null == t) {
			throw new NullPointerException("排序元素不能为空");
		}
		if (null == root) {
			root = new Node();
			root.data = t;
			return root;
		}
		if (parent.data.compareTo(t) > 0) {
			if (null == parent.left) {
				parent.left = new Node();
				parent.left.data = t;
				return parent.left;
			} else {
				return creatNext(parent.left, t);
			}
		} else {
			if (null == parent.right) {
				parent.right = new Node();
				parent.right.data = t;
				return parent.right;
			} else {
				return creatNext(parent.right, t);
			}
		}
		
	}
	
	public List<T> toList(){
		List<T> list = new ArrayList<>();
		midOrder(root, list);
		return list;
	}
	
	private void midOrder(Node parent, List<T> list){
		if (null == parent) {
			return;
		}
		midOrder(parent.left, list);
		list.add(parent.data);
		midOrder(parent.right, list);
	}
	
	/*
	 * 节点
	 */
	private class Node {
		private T data;
		private Node left;
		private Node right;
	}
	
	
	public static void main(String[] args) {
		SortBitTree<Integer> tree = new SortBitTree<>();
		tree.add(3);
		tree.add(4);
		tree.add(5);
		tree.add(9);
		tree.add(7);
		tree.add(3);
		tree.add(8);
		tree.add(0);
		List<Integer> list = tree.toList();
		System.out.println(list);
	}
}
