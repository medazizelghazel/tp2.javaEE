import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class liste_produit extends HttpServlet {
    private List<Product> productList = new ArrayList<Product>();
    private static final long serialVersionUID = 1L;

    public liste_produit() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        displayProducts(response);  
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addProduct(request);
        } else if ("edit".equals(action)) {
            editProduct(request);
        } else if ("delete".equals(action)) {
            deleteProduct(request);
        }
        displayProducts(response);
    }

    private void addProduct(HttpServletRequest request) {
        String name = request.getParameter("produit");
        double price = Double.parseDouble(request.getParameter("prix"));
        int quantity = Integer.parseInt(request.getParameter("quantite"));

        productList.add(new Product(name, price, quantity));
    }

    private void editProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        for (Product p : productList) {
            if (p.getId() == id) {
                p.setName(request.getParameter("produit"));
                p.setPrice(Double.parseDouble(request.getParameter("prix")));
                p.setQuantity(Integer.parseInt(request.getParameter("quantite")));
                break;
            }
        }
    }

    private void deleteProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
		productList.remove(id);
    }

    private void displayProducts(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><body>");
        out.println("<h2>Liste des Produits</h2>");
        if (productList.isEmpty()) {
            out.println("<p>Aucun produit trouvé. La liste est vide.</p>");
            out.println("<br><a href='inpute_produit.html'>Ajouter un produit</a>");
        } else {
	        out.println("<table border='1'>");
	        out.println("<tr><th>Produit</th><th>Prix</th><th>Quantité</th><th>Actions</th></tr>");
	        
	
	        for (Product p : productList) {
	            out.println("<tr>");
	            out.println("<td>" + p.getName() + "</td>");
	            out.println("<td>" + p.getPrice() + "</td>");
	            out.println("<td>" + p.getQuantity() + "</td>");
	            out.println("<td>");
	            out.println("<form action='liste_produit' method='POST' style='display:inline;'>");
	            out.println("<input type='hidden' name='id' value='" + p.getId() + "'>");
	            out.println("<input type='text' name='produit' value='" + p.getName() + "'>");
	            out.println("<input type='text' name='prix' value='" + p.getPrice() + "'>");
	            out.println("<input type='text' name='quantite' value='" + p.getQuantity() + "'>");
	            out.println("<input type='hidden' name='action' value='edit'>");
	            out.println("<button type='submit'>Edit</button>");
	            out.println("</form>");
	
	            
	            out.println("<form action='liste_produit' method='POST' style='display:inline;'>");
	            out.println("<input type='hidden' name='id' value='" + p.getId() + "'>");
	            out.println("<input type='hidden' name='action' value='delete'>");
	            out.println("<button type='submit'>Delete</button>");
	            out.println("</form>");
	            out.println("</td>");
	            out.println("</tr>");
	        }
	
	        out.println("</table>");
	        out.println("<br><a href='inpute_produit.html'>Ajouter un produit</a>");
	        out.println("</body></html>");
	    }
    }
}
