/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentbjsharron.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;

/**
 * The implementation for my version of Hanto.
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	final private int x, y;
	
	/**
	 * The only constructor.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * @param coordinate an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate)
	{
		this(coordinate.getX(), coordinate.getY());
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
	
	/**
	 * Tests if two HantoCoordinateImpls are adjacent to each other
	 * @param other coordinate to test for adjacency against this
	 * @return true if this is adjacent to other
	 */
	public boolean isAdjacentTo(HantoCoordinateImpl other) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = other.getX();
		int y2 = other.getY();
		
		if (x1 + 1 == x2) {
			if (y1 == y2 || y1 - 1 == y2) {
				return true;
			}
		} else if (x1 == x2) {
			if (y1 + 1 == y2 || y1 - 1 == y2) {
				return true;
			}
		} else if (x1 - 1 == x2) {
			if (y1 == y2 || y1 + 1 == y2) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Gets a List of the six adjacent coordinates
	 * @return List of adjacent coordinates
	 */
	public List<HantoCoordinateImpl> getAdjacentCoordinates()  {
		List<HantoCoordinateImpl> adjacencies = new ArrayList<HantoCoordinateImpl>();
		
		int x = this.getX();
		int y = this.getY();
		
		adjacencies.add(new HantoCoordinateImpl(x + 1, y));
		adjacencies.add(new HantoCoordinateImpl(x + 1, y - 1));
		adjacencies.add(new HantoCoordinateImpl(x, y + 1));
		adjacencies.add(new HantoCoordinateImpl(x, y - 1));
		adjacencies.add(new HantoCoordinateImpl(x - 1, y));
		adjacencies.add(new HantoCoordinateImpl(x - 1, y + 1));
		
		return adjacencies;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HantoCoordinateImpl)) {
			return false;
		}
		final HantoCoordinateImpl other = (HantoCoordinateImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	public int distanceTo(HantoCoordinateImpl to) {
		return Math.abs(x - to.x) + Math.abs(y - to.y);
	}
}
