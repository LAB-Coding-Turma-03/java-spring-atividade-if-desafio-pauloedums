package br.com.impacta.lab.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.impacta.lab.entities.Product;
import br.com.impacta.lab.entities.TypePayment;

@RestController
@RequestMapping("/simular")
public class SimularValoresController {

	public String product = "";
	public String payment = "";
	public double productValue = 0.0;
	public double productValueAfterDiscount = 0.0;
	public String message = "";

	@GetMapping
	public ResponseEntity<String> simularValores(@RequestParam(name="codigoProduto") int codigoProduto,
			@RequestParam(name="codTipoPagamento") int codTipoPagamento) {

		/* Lista dos produtos */
		List<Product> products = new ArrayList<>();
			products.add(new Product(1, "Camisa", 70.00));
			products.add(new Product(2, "Shorts", 57.50));
			products.add(new Product(3, "Meia", 9.99));
			products.add(new Product(4, "Toca", 35.00));
			products.add(new Product(5, "Luvas", 19.50));

		/* Lista dos tipos de pagamento */
		List<TypePayment> paymentTypes = new ArrayList<>();
			paymentTypes.add(new TypePayment(1, "A vista no dinheiro com 10% de desconto"));
			paymentTypes.add(new TypePayment(2, "A vista no cartão de crédito  com 5% de desconto"));
			paymentTypes.add(new TypePayment(3, "Em duas parcelas sem nenhum desconto"));
			paymentTypes.add(new TypePayment(4, "Em três vezes com 10% de juros"));
		
		/* condicional para um código válido */
		if(codigoProduto != 0 && codTipoPagamento != 0) {
			
			/* condicional para um código válido, pórem fora do range de produtos */
			if(codigoProduto > products.size()) {
				message = "Não foi encontrado nenhum produto, por favor tente novamente.";
			}
			/* condicional para um código válido, pórem fora do range de tipos de pagamento */
			else if(codTipoPagamento > paymentTypes.size()) {
				message = "Não foi encontrado nenhum tipo de pagamento, por favor tente novamente.";
			}
			/* condicional para um código válido e um tipo de pagamento válido */
			else {
				Product resultProduct = products.stream()
				.filter(p -> p.getCode() == codigoProduto).findFirst().get();
				
				paymentTypes.stream().forEach(p -> {
					if(p.getCode() == codTipoPagamento){
						payment = p.getDescription();
						product = resultProduct.getDescription();
						productValue = resultProduct.getValue();

						switch(codTipoPagamento) {
							case 1: 
								productValueAfterDiscount = (productValue - (productValue * 0.10));
								break;
							case 2: 
								productValueAfterDiscount = (productValue - (productValue * 0.05));
								break;
							case 3:
								productValueAfterDiscount = productValue;
								break;
							case 4: 
								productValueAfterDiscount = (productValue * 0.10) + productValue;
								break;
							default: break;
						}
					}
				});
				message = product + " sendo pago " + payment + " custará " + productValueAfterDiscount + " reais";
			}
		}
		/* condicional para um código de produto inválido */
		else if(codigoProduto == 0 || codigoProduto <= 0){
			message = "Por favor, digite um código de produto válido";
		}
		/* condicional para um tipo de pagamento inválido */
		else if(codTipoPagamento == 0 || codTipoPagamento <= 0){
			message = "Por favor, digite um tipo de pagamento válido";
		}		
		/* condicional para valores inválidos ou sem inventário */
		else {
			message = "Sua pesquisa não retornou nenhum dado, por favor tente novamente.";
		}

		return ResponseEntity.ok(message);
	}
	
}
