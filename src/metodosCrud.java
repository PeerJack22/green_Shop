import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

public class metodosCrud {

    private final MongoCollection<Document> collection;

    //Conexion con la colección de productos
    public metodosCrud(MongoDatabase database) {
        this.collection = database.getCollection("productos");
    }

    //Crear un nuevo producto
    public void crearProducto(String name, String description, double price, int stock) {
        Document nuevoProducto = new Document("name", name)
                .append("description", description) //Se agrega en la coleccion
                .append("price", price)
                .append("stock", stock);
        collection.insertOne(nuevoProducto);
        System.out.println("Producto creado con éxito."); //mensaje de confirmación
    }

    //Ver todos los productos
    public void leerProductos() {
        FindIterable<Document> productos = collection.find();
        for (Document producto : productos) {
            System.out.println(producto.toJson());
        }
    }

    //Ver producto por id
    public Document leerProductoPorId(String id) {
        return collection.find(new Document("_id", new ObjectId(id))).first();
    }

    // Actualizar producti por id
    public void actualizarProducto(String id, String name, String description, double price, int stock) {
        Document filtro = new Document("_id", new ObjectId(id));
        Document actualizacion = new Document("$set", new Document("name", name)
                .append("description", description)
                .append("price", price)
                .append("stock", stock));
        collection.updateOne(filtro, actualizacion);
        System.out.println("Producto actualizado con éxito.");
    }

    // Eliminar producto por id
    public void eliminarProducto(String id) {
        Document filtro = new Document("_id", new ObjectId(id));
        collection.deleteOne(filtro);
        System.out.println("Producto eliminado con éxito.");
    }

}
