/**
 * Copyright 2013 DuraSpace, Inc.
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

package org.fcrepo.webhooks;

import static com.hp.hpl.jena.rdf.model.ModelFactory.createDefaultModel;
import static org.fcrepo.kernel.RdfLexicon.HAS_SUBSCRIPTION_SERVICE;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import org.fcrepo.kernel.FedoraResource;
import org.fcrepo.http.commons.api.rdf.UriAwareResourceModelFactory;
import org.fcrepo.kernel.rdf.IdentifierTranslator;
import org.fcrepo.jcr.FedoraJcrTypes;
import org.springframework.stereotype.Component;

import javax.jcr.RepositoryException;
import javax.ws.rs.core.UriInfo;

/**
 * Register webhooks resource URIs for the root object
 */
@Component
public class WebhooksResources implements UriAwareResourceModelFactory {

    @Override
    public Model createModelForResource(final FedoraResource resource, final UriInfo uriInfo,
        final IdentifierTranslator graphSubjects) throws RepositoryException {
        final Model model = createDefaultModel();
        final Resource s = graphSubjects.getSubject(resource.getNode().getPath());

        if (resource.getNode().getPrimaryNodeType().isNodeType(
                FedoraJcrTypes.ROOT)) {
            model.add(s, HAS_SUBSCRIPTION_SERVICE, model
                    .createResource(uriInfo.getBaseUriBuilder().path(
                            FedoraWebhooks.class).build().toASCIIString()));
        }

        return model;
    }
}
