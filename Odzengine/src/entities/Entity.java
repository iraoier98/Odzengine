package entities;

public class Entity {
	
	private int vao;
	private int vertexCount;
	
	
	public Entity(int vao, int vertexCount) {
		this.vao = vao;
		this.vertexCount = vertexCount;
	}

	public int getVAO() {
		return vao;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	

}
