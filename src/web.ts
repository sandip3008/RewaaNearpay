import { WebPlugin } from '@capacitor/core';

import type { RewaaNearpayPlugin } from './definitions';

export class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async initNearpay(options: { token: string }): Promise<{ token: string }> {
    console.log('initNearpay', options);
    return options;
  }
  async setupNearpay(options: { token: string }): Promise<{ token: string }> {
    console.log('setupNearpay', options);
    return options;
  }
  async initPayment(options: { token: string }): Promise<{ token: string }> {
    console.log('initPayment', options);
    return options;
  }
  async purchase(options: { amount: string, token: string }): Promise<{ amount: string, token: string }> {
    console.log('purchase amount: ', options);
    return options;
  }
  async reconcile(options: { enableReceiptUi: boolean, token: string }): Promise<{ enableReceiptUi: boolean, token: string }> {
    console.log('reconcile: ', options);
    return options;
  }
  async refund(options: { enableReceiptUi: boolean, amount: number, transactionReferenceRetrievalNumber: string, customerReferenceNumber: number, token: string }): Promise<{ enableReceiptUi: boolean, amount: number, transactionReferenceRetrievalNumber: string, customerReferenceNumber: number, token: string }> {
    console.log('refund: ', options);
    return options;
  }
  async reverse(options: { enableReceiptUi: boolean; transactionUuid: string, token: string; }): Promise<{ enableReceiptUi: boolean; transactionUuid: string, token: string; }> {
    return options;
  }
}
