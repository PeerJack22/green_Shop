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
    public void crearProducto(String nombre, String descripcion, double precio, int stock) {
        Document nuevoProducto = new Document("nombre", nombre)
                .append("descripcion", descripcion) //Se agrega en la coleccion
                .append("precio", precio)
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
