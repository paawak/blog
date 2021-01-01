import MenuAction from "./MenuAction";

interface MenuActionListener {
    (action: MenuAction): void;
  }

export default MenuActionListener;