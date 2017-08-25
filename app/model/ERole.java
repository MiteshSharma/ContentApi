package model;

import pojo.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mitesh on 18/11/16.
 */
public enum  ERole {
    ADMIN(Scope.All),
    MEMBER(Scope.All);

    private List<Scope> scopes;
    ERole(Scope ... scopes) {
        this.scopes = Arrays.asList(scopes);
    }
    public List<Scope> getScopes() {
        return this.scopes;
    }
}