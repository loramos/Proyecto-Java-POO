/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.pro2.core;

final public class Par<U, S> {
	private U a;
	private S b;
	
	public Par(U a, S b)
	{
		this.a = a;
		this.b = b;
	}
	
	public U getA() {
		return a;
	}
	
	public S getB() {
		return b;
	}

        public void setA(U a) {
            this.a = a;
        }

        public void setB(S b) {
            this.b = b;
        }	
        
	public boolean equals(Par<U,S> p)
	{
		return this.a.equals(p.getA()) && this.b.equals(p.getB());
	}
	
	@Override
	public String toString()
	{
		return String.format( "(%s, %s)", getA(), getB() );
	}
}
