
public class Zamowienie {
		String client_id;
		long request_id;
		String name;
		int quantity;
		double price;
		Zamowienie(final String client_id, final long request_id, final String name, final int quantity, final double price) {
			
			this.client_id = client_id;
			this.request_id = request_id;
			this.name = name;
			this.quantity = quantity;
			this.price = price;
		}
		public final String getClient_id() {
			return client_id;
		}
		public final void setClient_id(final String client_id) {
			this.client_id = client_id;
		}
		public final long getRequest_id() {
			return request_id;
		}
		public final void setRequest_id(final long request_id) {
			this.request_id = request_id;
		}
		public final String getName() {
			return name;
		}
		public final void setName(final String name) {
			this.name = name;
		}
		public final int getQuantity() {
			return quantity;
		}
		public final void setQuantity(final int quantity) {
			this.quantity = quantity;
		}
		public final double getPrice() {
			return price;
		}
		public final void setPrice(final double price) {
			this.price = price;
		}
		@Override
        public final String toString() {
			return client_id + "," + request_id + "," +  name
					+ "," +  quantity + "," +  price;
		}
		public final String toString2() {
			return client_id + "," + request_id + "," +  name
					+ "," +  quantity + "," + price;
		}
		
}
