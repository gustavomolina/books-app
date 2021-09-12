import React from "react";
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <div className="ui secondary pointing menu">
      <Link to="/" className="item">
         Aplicação para acompanhamento de livros - POC IBM
      </Link>
      <div className="right menu">
        <Link to="/" className="item">
          Início
        </Link>
      </div>
    </div>
  );
};

export default Header;
