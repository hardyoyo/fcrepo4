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

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import org.fcrepo.kernel.FedoraResource;
import org.fcrepo.kernel.RdfLexicon;
import org.fcrepo.http.commons.api.rdf.UriAwareResourceModelFactory;
import org.fcrepo.kernel.rdf.GraphSubjects;
import org.fcrepo.jcr.FedoraJcrTypes;
import org.fcrepo.kernel.utils.iterators.RdfStream;
import org.springframework.stereotype.Component;

import javax.jcr.RepositoryException;
import javax.ws.rs.core.UriInfo;

/**
 * Register webhooks resource URIs for the root object
 */
@Component
public class WebhooksResources implements UriAwareResourceModelFactory {

    @Override
    public RdfStream createModelForResource(FedoraResource resource,
            UriInfo uriInfo, GraphSubjects graphSubjects)
        throws RepositoryException {
        final Node s = graphSubjects.getGraphSubject(resource.getNode()).asNode();

        final RdfStream triples = new RdfStream();

        if (resource.getNode().getPrimaryNodeType().isNodeType(
                FedoraJcrTypes.ROOT)) {
            triples.concat(Triple.create(s, RdfLexicon.HAS_SUBSCRIPTION_SERVICE.asNode(), NodeFactory.createURI(uriInfo.getBaseUriBuilder().path(FedoraWebhooks.class).build().toASCIIString())));
        }

        return triples;

    }
}
