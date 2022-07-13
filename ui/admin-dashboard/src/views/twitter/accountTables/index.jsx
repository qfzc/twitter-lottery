import { columnsDataDevelopment } from 'views/twitter/accountTables/variables/columnsData';
import AccountTable from 'views/twitter/accountTables/AccountTable';

export default function Settings() {
    return <AccountTable columnsData={columnsDataDevelopment} />;
}
