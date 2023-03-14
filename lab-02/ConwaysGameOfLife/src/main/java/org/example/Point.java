package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {
		//TODO: insert logic which updates according to currentState and 
		//number of active neighbors
//		int numOfActiveNeighbors = this.getNumOfAliveNeighbors();

//		23/2
//		if(this.getState() == 1) {
//			if (numOfActiveNeighbors == 2 || numOfActiveNeighbors == 3) {
//				this.nextState = 1;
//			}
//			else this.nextState = 0;
//		}
//		else{
//			if(numOfActiveNeighbors == 3)
//				this.nextState = 1;
//			else this.nextState = 0;
//		}

		// 2345/45678
//		List<Integer> numForAlive = Arrays.asList(2,3,4,5);
//		List<Integer> numForDead = Arrays.asList(4,5,6,7,8);
//
//		if(this.getState() == 1) {
//			if (numForAlive.contains(numOfActiveNeighbors))
//				this.nextState = 1;
//			else this.nextState = 0;
//		}
//		else{
//			if(numForDead.contains(numOfActiveNeighbors))
//				this.nextState = 1;
//			else this.nextState = 0;
//		}

		// 45678/3
//		List<Integer> numForAlive = Arrays.asList(4,5,6,7,8);
//		List<Integer> numForDead = Arrays.asList(3);
//
//		if(this.getState() == 1) {
//			if (numForAlive.contains(numOfActiveNeighbors))
//				this.nextState = 1;
//			else this.nextState = 0;
//		}
//		else{
//			if(numForDead.contains(numOfActiveNeighbors))
//				this.nextState = 1;
//			else this.nextState = 0;
//		}

		if (this.getState() > 0){
			this.nextState = currentState - 1;
		}
		else {
			if(!neighbors.isEmpty() && this.neighbors.get(0).getState() > 0)
				this.nextState = 6;
		}
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
	
	//TODO: write method counting all active neighbors of THIS point
	public int getNumOfAliveNeighbors(){
		int count = 0;
		for(Point nei: this.neighbors){
			if(nei.getState() == 1){
				++count;
			}
		}
		return count;
	}

	public void drop(){
		if(Math.random() < 0.05){
			this.setState(6);
		}
	}
}
