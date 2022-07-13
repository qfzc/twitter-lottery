import { columnsDataDevelopment } from 'views/twitter/watchTables/variables/columnsData';
import WatchUserTable from 'views/twitter/watchTables/WatchUserTable';

export default function Settings() {
    return <WatchUserTable columnsData={columnsDataDevelopment} />;
}
