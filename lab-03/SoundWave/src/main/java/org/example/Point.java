package org.example;

public class Point {

	public Point nNeighbor;
	public Point wNeighbor;
	public Point eNeighbor;
	public Point sNeighbor;
	public float nVel;
	public float eVel;
	public float wVel;
	public float sVel;
	public float pressure;

	int type;
	public static Integer []types = {0,1,2};

	int sinInput;

	public Point() {
		clear();
		type = 0;
		sinInput = 1;
	}

	public void clicked() {
		pressure = 1;
	}
	
	public void clear() {
		nVel = 0;
		eVel = 0;
		wVel = 0;
		sVel = 0;
		pressure = 0;
		// TODO: clear velocity and pressure
	}

	public void updateVelocity() {
		// TODO: velocity update
		nVel = nVel - (nNeighbor.pressure - pressure);
		eVel = eVel - (eNeighbor.pressure - pressure);
		sVel = sVel - (sNeighbor.pressure - pressure);
		wVel = wVel - (wNeighbor.pressure - pressure);
	}

	public void updatePresure() {
		// TODO: pressure update
		if (type == 2){
			double A = 0.4;
			double f = 10;

			double radians = Math.toRadians(f*2*Math.PI*sinInput);
			pressure = (float) (A*Math.sin(radians));
		}
		else if(type == 0) {
			pressure = (float) (pressure - 0.5 * (nVel + eVel + sVel + wVel));
		}
		sinInput++;
	}

	public float getPressure() {
		return pressure;
	}
}