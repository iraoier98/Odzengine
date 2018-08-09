package entities;

public class Model {
	
	private int vao;
	private int vertexCount;
	
	private int texture;
	
	
	public Model(int vao, int vertexCount, int texture) {
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.texture = texture;
	}

	public int getVAO() {
		return vao;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getTexture() {
		return texture;
	}
	
	public void setTexture(int texture) {
		this.texture = texture;
	}

}
