package core.utilities.baseUtilities;


import core.utilities.enums.UserProfileInfo;
import core.utilities.objects.Account;
import core.utilities.objects.CustomerCreation;
import core.utilities.objects.User;
import core.utilities.objects.UserType;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SqlManager extends BaseSqlManager {

    Random rand;
    HashMap<String, String> finalAccounts = new HashMap<>();
    List<String> finalAccnts = new ArrayList<>();

    public SqlManager() {
        rand = new Random();
    }

    @Override
    public User getUser( UserType userType ) {
        List<User> users = query(
                (resultSet, i) -> {
                    //User u = new User(Institutions.findById(userType.getScope()));
                    User u = new User();
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.email = resultSet.getString("email");
                    u.customerId = resultSet.getString("customer_id");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.institutionID = resultSet.getString("institution_id");
                    return u;
                },
                "SELECT * FROM qa.community_ui_users where usertype = ?",
                userType.getType()

        );

        if (users.size() == 0) {
            throw new RuntimeException(String.format("No user found with usertype %s", userType));
        } else {
            return users.get(rand.nextInt(users.size()));
        }
    }

    @Step("{method}")
    public User getCustomer ( String env, UserType userType ) {
        List<User> users = query(
                (resultSet, i) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    u.agentId = resultSet.getString("agent_entity");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.subUser = resultSet.getBoolean("sub_user");
                    u.receiveElectronicStatements = resultSet.getBoolean("electronic_statements");
                    u.email = resultSet.getString("email");

                    try {
                        u.first = resultSet.getString("name").split(" ", 2)[0];
                        u.last = resultSet.getString("name").split(" ", 2)[1];
                    } catch (Exception e) {
                        u.last = "";
                        u.first = "";
                    }
                    u.ssn = resultSet.getString("tax_id");
                    u.address.zip = resultSet.getString("zip");
                    u.birthday = resultSet.getString("birthday");
                    u.primaryPhone = resultSet.getString("primary_phone");
                    u.secondaryPhone = resultSet.getString("secondary_phone");
                    u.ownedAccount.accountNumber = resultSet.getString("password_reset_accnum");
                    return u;
                },
               "SELECT * FROM qa.community_ui_customers where usertype = ? and environment = ?",
                userType.getType(),
                env
        );
        if (users.size() == 0) {
            throw new RuntimeException(String.format("No user found in env %s with usertype %s", env, userType.getType()));
        } else {
            return users.get(rand.nextInt(users.size()));
        }
    }

    @Step("{method}")
    public User getCustomer ( String env, UserType userType, int inUse ) {

        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    u.agentId = resultSet.getString("agent_entity");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.subUser = resultSet.getBoolean("sub_user");
                    u.receiveElectronicStatements = resultSet.getBoolean("electronic_statements");
                    u.email = resultSet.getString("email");
                    u.first = resultSet.getString("name").split(" ", 2)[0];
                    try {
                        u.last = resultSet.getString("name").split(" ", 2)[1];
                    } catch (Exception e) {
                        u.last = "";
                    }
                    u.ssn = resultSet.getString("tax_id");
                    u.address.zip = resultSet.getString("zip");
                    u.birthday = resultSet.getString("birthday");
                    u.primaryPhone = resultSet.getString("primary_phone");
                    u.secondaryPhone = resultSet.getString("secondary_phone");
                    u.ownedAccount.accountNumber = resultSet.getString("password_reset_accnum");
                    return u;
                },
                "SELECT * FROM qa.community_ui_customers where usertype = ? and environment = ? and currently_inuse = ?",
                userType.getType(),
                env,
                inUse
        );

        return users.get(rand.nextInt(users.size()));
    }

    public void updateCurrentlyInUse ( String accessId, int inUse ) {
        executeDml("UPDATE qa.community_ui_customers set currently_inuse = ? where access_id = ? ",
                inUse,
                accessId
        );
        System.out.println("Access ID "+ accessId + " is set to " + inUse);
    }

    @Step("{method}")
    public User getSSOCustomer ( String environment, String userType ) {

        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    return u;
                },
                "SELECT * FROM qa.community_ui_sso_customers where usertype = ? and environment = ?",
                userType,
                environment
                );

        return users.get(rand.nextInt(users.size()));
    }

    @Step("{method}")
    public User getApproveSubUser ( String env, String focusId, UserType userType, int inUse ) {

        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    u.agentId = resultSet.getString("agent_entity");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.subUser = resultSet.getBoolean("sub_user");
                    u.receiveElectronicStatements = resultSet.getBoolean("electronic_statements");
                    u.email = resultSet.getString("email");
                    u.first = resultSet.getString("name").split(" ", 2)[0];
                    try {
                        u.last = resultSet.getString("name").split(" ", 2)[1];
                    } catch (Exception e) {
                        u.last = "";
                    }
                    u.ssn = resultSet.getString("tax_id");
                    u.address.zip = resultSet.getString("zip");
                    u.birthday = resultSet.getString("birthday");
                    u.primaryPhone = resultSet.getString("primary_phone");
                    u.secondaryPhone = resultSet.getString("secondary_phone");
                    return u;
                },
                "SELECT * FROM qa.community_ui_customers where usertype = ? and environment = ? and focus_entity = ? and currently_inuse = ?",
                userType.getType(),
                env,
                focusId,
                inUse
        );
        return users.get(rand.nextInt(users.size()));
    }

    @Step("{method}")
    public User getFocusCustomer ( String env, String focusId, UserType userType, int inUse ) {
        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    u.agentId = resultSet.getString("agent_entity");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.subUser = resultSet.getBoolean("sub_user");
                    u.receiveElectronicStatements = resultSet.getBoolean("electronic_statements");
                    u.email = resultSet.getString("email");
                    u.first = resultSet.getString("name").split(" ", 2)[0];
                    try {
                        u.last = resultSet.getString("name").split(" ", 2)[1];
                    } catch (Exception e) {
                        u.last = "";
                    }
                    u.ssn = resultSet.getString("tax_id");
                    u.address.zip = resultSet.getString("zip");
                    u.birthday = resultSet.getString("birthday");
                    u.primaryPhone = resultSet.getString("primary_phone");
                    u.secondaryPhone = resultSet.getString("secondary_phone");
                    return u;
                },
                "SELECT * FROM qa.community_ui_customers where usertype = ? and environment = ? and customer_id = ? and currently_inuse = ?",
                userType.getType(),
                env,
                focusId,
                inUse
        );
        return users.get(rand.nextInt(users.size()));
    }

    @Step("{method}")
    public User getCustomer ( String accessId  ) {
        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    u.agentId = resultSet.getString("agent_entity");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.subUser = resultSet.getBoolean("sub_user");
                    u.receiveElectronicStatements = resultSet.getBoolean("electronic_statements");
                    u.email = resultSet.getString("email");
                    u.first = resultSet.getString("name").split(" ", 2)[0];
                    try {
                        u.last = resultSet.getString("name").split(" ", 2)[1];
                    } catch (Exception e) {
                        u.last = "";
                    }
                    u.ssn = resultSet.getString("tax_id");
                    u.address.zip = resultSet.getString("zip");
                    u.birthday = resultSet.getString("birthday");
                    u.primaryPhone = resultSet.getString("primary_phone");
                    u.secondaryPhone = resultSet.getString("secondary_phone");
                    return u;
                },
                "SELECT * FROM qa.community_ui_customers where access_id = ?",
                accessId

        );

        return users.get(rand.nextInt(users.size()));
    }

    @Step("{method}")
    public List<User> getAllUsers(String environment, String type) {
        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.institutionID = resultSet.getString("institution_id");
                    u.environment = resultSet.getString("environment");
                    u.accessId = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    u.customerId = resultSet.getString("customer_id");
                    u.firstSecurityAnswer = resultSet.getString("first_security_answer");
                    u.secondSecurityAnswer = resultSet.getString("second_security_answer");
                    u.thirdSecurityAnswer = resultSet.getString("third_security_answer");
                    u.agentId = resultSet.getString("agent_entity");
                    u.focusEntity = resultSet.getString("focus_entity");
                    u.subUser = resultSet.getBoolean("sub_user");
                    u.receiveElectronicStatements = resultSet.getBoolean("electronic_statements");
                    u.email = resultSet.getString("email");
                    u.first = resultSet.getString("name").split(" ", 2)[0];
                    try {
                        u.last = resultSet.getString("name").split(" ", 2)[1];
                    } catch (Exception e) {
                        u.last = "";
                    }
                    u.ssn = resultSet.getString("tax_id");
                    u.address.zip = resultSet.getString("zip");
                    u.birthday = resultSet.getString("birthday");
                    u.primaryPhone = resultSet.getString("primary_phone");
                    u.secondaryPhone = resultSet.getString("secondary_phone");
                    return u;
                },
                "SELECT * FROM qa.community_ui_customers where environment = ? and usertype != 'FXIM' and usertype like ?",
                environment,
                type

        );

        return users;
    }

    @Step("{method}")
    public void createCustomer(User user, UserType userType) {
        executeDml("INSERT INTO qa.community_ui_customers (institution_id, " +
                "environment," +
                "usertype, " +
                "access_id, " +
                "password, " +
                "customer_id, " +
                "first_security_answer, " +
                "second_security_answer, " +
                "third_security_answer," +
                "electronic_statements," +
                "email," +
                "name," +
                "tax_id," +
                "zip," +
                "birthday, " +
                "primary_phone, " +
                "secondary_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.institutionID,
                user.environment,
                userType.getType(),
                user.accessId,
                user.password,
                user.customerId,
                user.firstSecurityAnswer,
                user.secondSecurityAnswer,
                user.thirdSecurityAnswer,
                user.receiveElectronicStatements ? 1 : 0,
                user.email,
                userType.getType() == "Retail" ? user.getFullName() : user.getBusinessName(),
                user.ssn,
                user.address.zip,
                user.birthday,
                user.primaryPhone,
                user.secondaryPhone);
    }


    @Step("{method}")
    public void updateAccessId( String accessId, String newAccessId ) {
        executeDml("UPDATE qa.community_ui_customers set access_id = ? where access_id = ?",
                newAccessId,
                accessId
        );
    }

    @Step("{method}")
    public void updateField( UserProfileInfo field, String accessId, String newVal ) {
        executeDml("UPDATE qa.community_ui_customers set " + field.getFieldName() + " = ? where access_id = ?",
                newVal,
                accessId
        );
        System.out.println("Changed the " + field.getFieldName() + " of " + accessId + " to " + newVal + ".\n");
    }

    @Step("{method}")
    public User getFximUser( UserType userType ) {
        List<User> users = query(
                ( resultSet, i ) -> {
                    User u = new User();
                    u.username = resultSet.getString("access_id");
                    u.password = resultSet.getString("password");
                    return u;
                },
                "SELECT * FROM qa.community_ui_customers where usertype = ?",
                userType.getType()
        );

        return users.get(0);
    }
    @Step("{method}")
    public List<String> getAccount( String institution_id, String accDef, String userType, int already_used, int count) {

        List<Account> accType = query(
                ( resultSet, i ) -> {
                    Account a = new Account();
                    a.accountNumber = resultSet.getString("acc_num");
                    a.accountDescription = resultSet.getString("acc_description");
                    return a;
                },
                "SELECT * FROM qa.community_new_accounts where institution_id = ? and acc_type = ? and already_used = ? and usertype = ?",
                institution_id,
                accDef,
                already_used,
                userType
        );
        for (int i = 0; i < count; i++) {
            finalAccnts.add(accType.get(i).accountNumber);
            finalAccounts.put(accType.get(i).accountNumber, accType.get(i).accountDescription);
        }
        return finalAccnts;
    }

    @Step("{method}")
    public List<String> getAccountWithUserType ( String institution_id, String accDef, String userType, int already_used, int count) {

        List<String> finalAccnts = new ArrayList<>();
        List<Account> accType = query(
                ( resultSet, i ) -> {
                    Account a = new Account();
                    a.accountNumber = resultSet.getString("acc_num");
                    a.accountDescription = resultSet.getString("acc_description");
                    return a;
                },
                "SELECT * FROM qa.community_new_accounts where institution_id = ? and acc_type = ? and usertype = ? and already_used = ?",
                institution_id,
                accDef,
                userType,
                already_used
        );
        for (int i = 0; i < count; i++) {
            finalAccnts.add(accType.get(i).accountNumber);
            finalAccounts.put(accType.get(i).accountNumber, accType.get(i).accountDescription);
        }
        return finalAccnts;
    }

    public HashMap<String, String> getAccountDescription(){
        System.out.println(finalAccounts);
        return finalAccounts;
    }

    @Step("{method}")
    public void updateAlreadyUsedInAccounts ( CustomerCreation customerCreation ) {
        for(int i=0; i<customerCreation.accountNumList.size(); i++) {
            executeDml("UPDATE qa.community_new_accounts set already_used = 1 where acc_num = ?",
                    customerCreation.accountNumList.get(i)

            );
        }
    }

    @Step("{method}")
    public void updateFocusId (String fcEntity, String suAccess){
        executeDml("UPDATE qa.community_ui_customers set focus_entity = ?, sub_user = 1 where access_id = ?",
                fcEntity,
                suAccess
        );
    }

    @Step("{method}")
    public void updateAgentEntity ( String entity, String accessId ) {
            executeDml("UPDATE qa.community_ui_customers set agent_entity = ? where access_id = ?",
                    entity,
                    accessId
            );
    }

    @Step("{method}")
    public void deleteUser ( String environment, String accessId ) {
        executeDml("DELETE from qa.community_ui_customers where environment = ? and access_id = ?",
                environment,
                accessId
                );
    }



    public void getSingleUser(String environment, String accessId){
        executeDml("select * FROM qa.community_ui_customers where environment = ? and access_id = ?",
                environment,
                accessId
        );
    }
}
