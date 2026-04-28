package eci.edu.byteProgramming.ejercicio.paper.util;

/**
 * Facade Pattern - Simplifica la interfaz compleja del sistema de tienda virtual.
 * Encapsula la interacción entre Inventory, ECIPayment, Notification y Facturation.
 * 
 * Esta clase proporciona una interfaz unificada y simple para:
 * - Consultar productos disponibles
 * - Procesar compras
 * - Generar facturas
 * - Enviar notificaciones
 */
public class VirtualStore {
    private Inventory inventory;
    private ECIPayment eciPayment;
    private Notification notification;
    private Facturation facturation;
    
    public VirtualStore() {
        this.inventory = new Inventory();
        this.eciPayment = new ECIPayment();
        this.notification = new Notification();
        this.facturation = new Facturation();
        
        // Registrar el observador de eventos de pago
        PaymentEventObserver paymentObserver = new PaymentEventObserver(inventory, facturation, notification);
        eciPayment.addObserver(paymentObserver);
    }
    
    /**
     * Procesa una compra completa en la tienda virtual.
     * Orquesta el flujo: validación de inventario -> procesamiento de pago -> notificaciones
     */
    public boolean processPurchase(PaymentFactory paymentFactory, double amount, String customerId,
                                   String description, String customerName, String customerEmail, 
                                   String productId) {
        
        System.out.println("\n🛒 VIRTUAL STORE: Processing Purchase");
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Validar disponibilidad en inventario
        Product product = inventory.getProduct(productId);
        if (product == null) {
            System.out.println("❌ Product not found: " + productId);
            return false;
        }
        
        int availableStock = inventory.getStock(productId);
        if (availableStock <= 0) {
            System.out.println("❌ Product out of stock: " + product.getName());
            return false;
        }
        
        System.out.println("✅ Product available: " + product.getName());
        System.out.println("   Stock: " + availableStock + " units");
        System.out.println("");
        
        // Procesar el pago
        boolean paymentSuccess = eciPayment.processPayment(paymentFactory, amount, customerId, 
                                                           description, customerName, customerEmail, productId);
        
        System.out.println("═══════════════════════════════════════════════════════\n");
        return paymentSuccess;
    }
    
    /**
     * Obtiene un producto por ID
     */
    public Product getProduct(String productId) {
        return inventory.getProduct(productId);
    }
    
    /**
     * Obtiene el stock disponible de un producto
     */
    public int getStock(String productId) {
        return inventory.getStock(productId);
    }
    
    /**
     * Lista todos los productos disponibles
     */
    public void listAvailableProducts() {
        System.out.println("\n📦 AVAILABLE PRODUCTS:");
        System.out.println("─────────────────────────────────────");
        
        String[] productIds = {"LAPTOP001", "PHONE001", "BOOK001"};
        for (String id : productIds) {
            Product product = inventory.getProduct(id);
            if (product != null) {
                int stock = inventory.getStock(id);
                String status = stock > 0 ? "✅ Available" : "❌ Out of Stock";
                System.out.printf("%s - %s - $%.2f - %d units - %s%n", 
                    id, product.getName(), product.getPrice(), stock, status);
            }
        }
        System.out.println("─────────────────────────────────────\n");
    }
    
    /**
     * Obtiene la instancia de Inventory (para casos de uso avanzados)
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * Obtiene la instancia de ECIPayment (para casos de uso avanzados)
     */
    public ECIPayment getECIPayment() {
        return eciPayment;
    }
}
