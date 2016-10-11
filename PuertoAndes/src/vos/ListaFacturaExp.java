package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFacturaExp {

	@JsonProperty(value="valor_facturado")
	private List<FacturaExp> valor_facturado;

	public ListaFacturaExp(@JsonProperty(value="valor_facturado")List<FacturaExp> valor_facturado) {
		super();
		this.valor_facturado = valor_facturado;
	}

	public List<FacturaExp> getValorFacturado() {
		return valor_facturado;
	}

	public void setValorFacturado(List<FacturaExp> valorFacturado) {
		this.valor_facturado = valorFacturado;
	}
	
	

}

