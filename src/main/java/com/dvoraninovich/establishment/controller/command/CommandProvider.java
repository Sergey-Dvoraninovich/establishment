package com.dvoraninovich.establishment.controller.command;

import com.dvoraninovich.establishment.controller.command.impl.*;
import com.dvoraninovich.establishment.controller.command.impl.admin.GoToAdminProfileCommand;
import com.dvoraninovich.establishment.controller.command.impl.admin.dish.*;
import com.dvoraninovich.establishment.controller.command.impl.admin.ingredient.CreateIngredientCommand;
import com.dvoraninovich.establishment.controller.command.impl.admin.ingredient.GoToCreateIngredientPageCommand;
import com.dvoraninovich.establishment.controller.command.impl.admin.ingredient.GoToIngredientsPageCommand;
import com.dvoraninovich.establishment.controller.command.impl.admin.order.*;
import com.dvoraninovich.establishment.controller.command.impl.admin.user.GoToUsersPageCommand;
import com.dvoraninovich.establishment.controller.command.impl.customer.*;

import java.util.EnumMap;

import static com.dvoraninovich.establishment.controller.command.CommandType.*;

public final class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandType, Command> commands = new EnumMap(CommandType.class);

    private CommandProvider() {
        commands.put(GO_TO_LOGIN_PAGE,new GoToLogInPageCommand());
        commands.put(LOGIN_PAGE,new LogInPageCommand());
        commands.put(SIGN_UP_PAGE, new SignUpPageCommand());
        commands.put(GO_TO_SIGN_UP_PAGE, new GoToSignUpPageCommand());
        commands.put(GO_TO_USERS_PAGE, new GoToUsersPageCommand());
        commands.put(GO_TO_INGREDIENTS_PAGE, new GoToIngredientsPageCommand());
        commands.put(GO_TO_CREATE_INGREDIENT_PAGE, new GoToCreateIngredientPageCommand());
        commands.put(CREATE_INGREDIENT, new CreateIngredientCommand());
        commands.put(DEFAULT,new DefaultCommand());
        commands.put(GO_TO_DISHES_PAGE, new GoToDishesPageCommand());
        commands.put(DISABLE_DISH, new DisableDishCommand());
        commands.put(MAKE_DISH_AVAILABLE, new MakeDishAvailableCommand());
        commands.put(GO_TO_CREATE_DISH, new GoToCreateDishCommand());
        commands.put(CREATE_DISH, new CreateDishCommand());
        commands.put(GO_TO_EDIT_DISH, new GoToEditDishCommand());
        commands.put(GO_TO_DISH_PAGE, new GoToDishPageCommand());
        commands.put(EDIT_DISH, new EditDishCommand());
        commands.put(ADD_DISH_INGREDIENT, new AddDishIngredientCommand());
        commands.put(REMOVE_DISH_INGREDIENT, new RemoveDishIngredientCommand());
        commands.put(VERIFY_CODE, new VerifyCodeCommand());
        commands.put(GO_TO_VERIFY_CODE_PAGE, new GoToVerifyCodePageCommand());
        commands.put(GO_TO_CUSTOMER_PROFILE_PAGE, new GoToCustomerProfilePageCommand());
        commands.put(GO_TO_ORDERS_PAGE, new GoToOrdersPageCommand());
        commands.put(GO_TO_CUSTOMER_BASKET, new GoToCustomerBasketCommand());
        commands.put(GO_TO_ADMIN_PAGE, new GoToAdminProfileCommand());
        commands.put(SIGN_OUT, new SignOutCommand());
        commands.put(UPLOAD_DISH_PHOTO, new EditDishPhoto());
        commands.put(EDIT_CUSTOMER, new EditCustomerCommand());
        commands.put(ADD_TO_BASKET, new AddDishToBasketCommand());
        commands.put(INCREMENT_ORDER_DISH, new IncrementOrderDishCommand());
        commands.put(DECREMENT_ORDER_DISH, new DecrementOrderDishCommand());
        commands.put(DELETE_ORDER_DISH, new DeleteOrderDishCommand());
        commands.put(BUY_BASKET, new BuyBasketCommand());
        commands.put(RECALCULATE_PRICE, new RecalculateOrderPriceCommand());
        commands.put(GO_TO_CUSTOMER_ORDERS, new GoToCustomerOrdersCommand());
        commands.put(GO_TO_ORDER_PAGE, new GoToOrderPageCommand());
        commands.put(UPLOAD_USER_PHOTO, new EditProfilePhotoCommand());
        commands.put(SET_ORDERS_FILTER_PARAMETERS, new SetOrdersFilterParametersCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(DEFAULT);
        }
        CommandType commandType;
        try {
            commandType = valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        return commands.get(commandType);
    }
}
