# rewaanearpay

This plugin gives you the ability to let your app accept payments and that includes many features such as purchase, refund and reconcile.

## Install

```bash
npm install rewaanearpay
npx cap sync
```

## API

<docgen-index>

* [`setupNearpay(...)`](#setupnearpay)
* [`initNearpay(...)`](#initnearpay)
* [`logoutNearpay(...)`](#logoutnearpay)
* [`purchase(...)`](#purchase)
* [`reconcile(...)`](#reconcile)
* [`refund(...)`](#refund)
* [`reverse(...)`](#reverse)
* [`getTransactionByUUID(...)`](#gettransactionbyuuid)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setupNearpay(...)

```typescript
setupNearpay(options: { token: string; }) => Promise<{ token: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ token: string; }</code> |

**Returns:** <code>Promise&lt;{ token: string; }&gt;</code>

--------------------


### initNearpay(...)

```typescript
initNearpay(options: { token: string; isProd: boolean; }) => Promise<{ token: string; }>
```

| Param         | Type                                             |
| ------------- | ------------------------------------------------ |
| **`options`** | <code>{ token: string; isProd: boolean; }</code> |

**Returns:** <code>Promise&lt;{ token: string; }&gt;</code>

--------------------


### logoutNearpay(...)

```typescript
logoutNearpay(options: { token: string; }) => Promise<{ token: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ token: string; }</code> |

**Returns:** <code>Promise&lt;{ token: string; }&gt;</code>

--------------------


### purchase(...)

```typescript
purchase(options: { amount: string; token: string; }) => Promise<{ amount: string; token: string; }>
```

| Param         | Type                                            |
| ------------- | ----------------------------------------------- |
| **`options`** | <code>{ amount: string; token: string; }</code> |

**Returns:** <code>Promise&lt;{ amount: string; token: string; }&gt;</code>

--------------------


### reconcile(...)

```typescript
reconcile(options: { reconcileId: string; enableReceiptUi: boolean; token: string; }) => Promise<{ reconcileId: string; enableReceiptUi: boolean; token: string; }>
```

| Param         | Type                                                                           |
| ------------- | ------------------------------------------------------------------------------ |
| **`options`** | <code>{ reconcileId: string; enableReceiptUi: boolean; token: string; }</code> |

**Returns:** <code>Promise&lt;{ reconcileId: string; enableReceiptUi: boolean; token: string; }&gt;</code>

--------------------


### refund(...)

```typescript
refund(options: { enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; token: string; }) => Promise<{ enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; token: string; }>
```

| Param         | Type                                                                                                                                                    |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; token: string; }</code> |

**Returns:** <code>Promise&lt;{ enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; token: string; }&gt;</code>

--------------------


### reverse(...)

```typescript
reverse(options: { enableReceiptUi: boolean; transactionUuid: string; token: string; }) => Promise<{ enableReceiptUi: boolean; transactionUuid: string; token: string; }>
```

| Param         | Type                                                                               |
| ------------- | ---------------------------------------------------------------------------------- |
| **`options`** | <code>{ enableReceiptUi: boolean; transactionUuid: string; token: string; }</code> |

**Returns:** <code>Promise&lt;{ enableReceiptUi: boolean; transactionUuid: string; token: string; }&gt;</code>

--------------------


### getTransactionByUUID(...)

```typescript
getTransactionByUUID(options: { transactionUUID: string; token: string; }) => Promise<{ transactionUUID: string; token: string; }>
```

| Param         | Type                                                     |
| ------------- | -------------------------------------------------------- |
| **`options`** | <code>{ transactionUUID: string; token: string; }</code> |

**Returns:** <code>Promise&lt;{ transactionUUID: string; token: string; }&gt;</code>

--------------------

</docgen-api>
