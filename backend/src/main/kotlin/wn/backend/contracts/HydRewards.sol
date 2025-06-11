// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

contract HydRewards {
    string public name = "HydRewards";
    string public symbol = "HYD";
    uint8 public decimals = 18;

    mapping(address => uint256) public balances;
    uint256 public totalSupply;
    address public owner;

    event Transfer(address indexed from, address indexed to, uint256 value);
    event Mint(address indexed to, uint256 value);

    constructor() {
        owner = msg.sender;
    }

    function balanceOf(address _owner) external view returns (uint256) {
        return balances[_owner];
    }

    function transfer(address _to, uint256 _amount) external {
        require(balances[msg.sender] >= _amount, "Insufficient balance");
        balances[msg.sender] -= _amount;
        balances[_to] += _amount;
        emit Transfer(msg.sender, _to, _amount);
    }

    function mint(address user, uint256 amount) external {
        require(msg.sender == owner, "Only owner can mint");
        balances[user] += amount;
        totalSupply += amount;
        emit Mint(user, amount);
        emit Transfer(address(0), user, amount);
    }
}