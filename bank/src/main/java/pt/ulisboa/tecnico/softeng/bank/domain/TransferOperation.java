package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class TransferOperation extends TransferOperation_Base {

    public void init(WithdrawOperation withdrawOperation, DepositOperation depositOperation, String transactionSource,
                     String transactionReference) {
        checkArguments(withdrawOperation, depositOperation, transactionSource, transactionReference);

        setWithdrawOperation(withdrawOperation);
        setDepositOperation(depositOperation);

        setTransactionSource(transactionSource);
        setTransactionReference(transactionReference);

        init(withdrawOperation.getBank());
    }

    private void checkArguments(WithdrawOperation withdrawOperation, DepositOperation depositOperation,
                                String transactionSource, String transactionReference) {
        if (withdrawOperation == null || depositOperation == null
                || transactionSource == null || transactionSource.trim().equals("")
                || transactionReference == null || transactionReference.trim().equals("")) {
            throw new BankException();
        }
    }

    @Override
    public void delete() {
        if (getWithdrawOperation() != null) {
            WithdrawOperation withdrawOperation = getWithdrawOperation();
            setWithdrawOperation(null);
            withdrawOperation.delete();
        }

        if (getDepositOperation() != null) {
            DepositOperation depositOperation = getDepositOperation();
            setDepositOperation(null);
            depositOperation.delete();
        }

        super.delete();
    }

    @Override
    public Operation.Type getType() {
        return Type.TRANSFER;
    }

    @Override
    protected String doRevert() {
        if (!canRevert()) {
            throw new BankException();
        }

        WithdrawOperation withdrawOperation = getDepositOperation().getSourceAccount().withdraw(getDepositOperation().getValue());
        getDepositOperation().setCancellation(getDepositOperation().getReference() + "_CANCEL");

        DepositOperation depositOperation = getWithdrawOperation().getSourceAccount().deposit(getWithdrawOperation().getValue());
        getWithdrawOperation().setCancellation(getWithdrawOperation().getReference() + "_CANCEL");

        TransferOperation transferOperation = new TransferOperation();
        transferOperation.init(withdrawOperation, depositOperation, "REVERT", getReference());

        return transferOperation.getReference();

    }

    @Override
    public Account getSourceAccount() {
        return getWithdrawOperation().getAccount();
    }

    @Override
    public Account getTargetAccount() {
        return getDepositOperation().getAccount();
    }

    @Override
    public String getSourceIban() {
        return getSourceAccount().getIban();
    }

    @Override
    public String getTargetIban() {
        return getTargetAccount().getIban();
    }

    @Override
    public long getValue() {
        return getWithdrawOperation().getValue();
    }

    @Override
    public boolean canRevert() {
        return !getTransactionSource().equals("REVERT") && getCancellation() == null;
    }

    @Override
    public boolean isSubOperation() {
        return false;
    }


}
