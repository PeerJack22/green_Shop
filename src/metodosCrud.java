import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;


public class metodosCrud {

    public metodosCrud() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("greenShop");
        this.collection = database.getCollection("productos");
    }

    private final MongoCollection<Document> collection;

    //Conexion con la colección de productos
    public metodosCrud(MongoDatabase database) {
        this.collection = database.getCollection("productos");
    }

    //Crear un nuevo producto
    public void crearProducto(String nombre, String descripcion, double precio, int stock) {
        Document nuevoProducto = new Document("nombre", nombre)
                .append("descripcion", descripcion) //Se agrega en la coleccion
                .append("precio", precio)
                .append("stock", stock);
        collection.insertOne(nuevoProducto);
        System.out.println("Producto creado con éxito."); //mensaje de confirmación
    }

    //Ver todos los productos
    public List<Document> leerProductos() {
        List<Document> listaProductos = new ArrayList<>();
        FindIterable<Document> productos = collection.find();
        for (Document producto : productos) {
            listaProductos.add(producto);
        }
        return listaProductos;
    }

    // Actualizar producti por id
    public void actualizarProducto(String id, String nombre, String descripcion, double precio, int stock) {
        Document filtro = new Document("_id", new ObjectId(id));
        Document actualizacion = new Document("$set", new Document("nombre", nombre)
                .append("descripcion", descripcion)
                .append("precio", precio)
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