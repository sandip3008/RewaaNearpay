import { WebPlugin } from '@capacitor/core';
import type { RewaaNearpayPlugin } from './definitions';
export declare class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
    initNearpay(options: {
        token: string;
        isProd: boolean;
    }): Promise<{
        token: string;
    }>;
    setupNearpay(options: {
        token: string;
    }): Promise<{
        token: string;
    }>;
    initPayment(options: {
        token: string;
    }): Promise<{
        token: string;
    }>;
    logoutNearpay(options: {
        token: string;
    }): Promise<{
        token: string;
    }>;
    purchase(options: {
        amount: string;
        token: string;
    }): Promise<{
        amount: string;
        token: string;
    }>;
    reconcile(options: {
        reconcileId: string;
        enableReceiptUi: boolean;
        token: string;
    }): Promise<{
        reconcileId: string;
        enableReceiptUi: boolean;
        token: string;
    }>;
    refund(options: {
        enableReceiptUi: boolean;
        amount: number;
        transactionReferenceRetrievalNumber: string;
        customerReferenceNumber: number;
        token: string;
    }): Promise<{
        enableReceiptUi: boolean;
        amount: number;
        transactionReferenceRetrievalNumber: string;
        customerReferenceNumber: number;
        token: string;
    }>;
    reverse(options: {
        enableReceiptUi: boolean;
        transactionUuid: string;
        token: string;
    }): Promise<{
        enableReceiptUi: boolean;
        transactionUuid: string;
        token: string;
    }>;
    getTransactionByUUID(options: {
        transactionUUID: string;
        token: string;
    }): Promise<{
        transactionUUID: string;
        token: string;
    }>;
}
