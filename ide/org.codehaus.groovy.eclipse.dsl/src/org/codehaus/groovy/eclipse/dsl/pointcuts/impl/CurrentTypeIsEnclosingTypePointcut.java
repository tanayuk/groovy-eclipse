/*
 * Copyright 2003-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.eclipse.dsl.pointcuts.impl;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.eclipse.dsl.pointcuts.AbstractPointcut;
import org.codehaus.groovy.eclipse.dsl.pointcuts.BindingSet;
import org.codehaus.groovy.eclipse.dsl.pointcuts.GroovyDSLDContext;
import org.codehaus.groovy.eclipse.dsl.pointcuts.PointcutVerificationException;

/**
 * Matches when the current type is the same as the enclosing type.  This matches true
 * for references to 'this', or when a new expression is being started.  This pointcut 
 * takes no arguments
 * @author andrew
 * @created Apr 1, 2011
 */
public class CurrentTypeIsEnclosingTypePointcut extends AbstractPointcut {

    public CurrentTypeIsEnclosingTypePointcut(String containerIdentifier, String pointcutName) {
        super(containerIdentifier, pointcutName);
    }

    @Override
    public BindingSet matches(GroovyDSLDContext pattern) {
        ClassNode enclosing = pattern.getCurrentScope().getEnclosingTypeDeclaration();
        ClassNode current = pattern.getCurrentType();
        if (enclosing != null && current != null && enclosing.redirect() == current.redirect()) {
            return new BindingSet(current);
        }
        return null;
    }
    
    @Override
    public void verify() throws PointcutVerificationException {
        String noArgs = hasNoArgs();
        if (noArgs != null) {
            throw new PointcutVerificationException(noArgs, this);
        }
        super.verify();
    }

}