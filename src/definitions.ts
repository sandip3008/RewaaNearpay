export interface RewaaNearpayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  setupNearpay(options: { token: string }): Promise<{ token: string }>;
  initNearpay(options: { token: string; }): Promise<{ token: string; }>;
  purchase(options: { amount: string, token: string }): Promise<{ amount: string, token: string }>;
  reconcile(options: { enableReceiptUi: boolean, token: string }): Promise<{ enableReceiptUi: boolean, token: string }>;
  refund(options: { enableReceiptUi: boolean, amount: number, transactionReferenceRetrievalNumber: string, customerReferenceNumber: number, token: string }): Promise<{ enableReceiptUi: boolean, amount: number, transactionReferenceRetrievalNumber: string, customerReferenceNumber: number, token: string }>;
  reverse(options: { enableReceiptUi: boolean, transactionUuid: string, token: string }): Promise<{ enableReceiptUi: boolean, transactionUuid: string, token: string }>;
}
