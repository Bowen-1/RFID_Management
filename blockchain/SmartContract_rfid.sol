pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "./Table.sol";

contract rfid {
    event AddRfidResult(int256 count);
    event GetRfidResult(string name, string location);


    TableFactory tableFactory;
    string constant TABLE_NAME = "rfid";

    constructor() public {
        //The fixed address is 0x1001 for TableFactory
        tableFactory = TableFactory(0x1001);
        tableFactory.createTable(TABLE_NAME, "id", "name,location");
    }
    
    
    
    //set record
    function addRfid(string memory id, string memory name,  string memory location)
        public
        returns (int256)
    {
        Table table = tableFactory.openTable(TABLE_NAME);
        
        Entry entry = table.newEntry();
        entry.set("id", id);
        entry.set("name", name);
        entry.set("location", location);
        int256 count = table.insert(id, entry);
        emit AddRfidResult(count);
        return count;
    }
    
    

    //get record
    function get(string memory id) 
    public 
    view returns
    (string memory, string memory) {
        Table table = tableFactory.openTable(TABLE_NAME);
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);
        
        Entries entries = table.select(id, condition);
        
        require(entries.size()>  0, "RFID does not exist");
        Entry entry = entries.get(0);
        
        string memory name = entry.getString("name");
        string memory location = entry.getString("location");
        
        emit GetRfidResult(name,location);
        
        return (name,location);
        
    }

}