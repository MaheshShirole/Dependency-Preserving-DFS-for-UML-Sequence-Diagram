# Dependency-Preserving-DFS-for-UML-Sequence-Diagram
This repository contains the DP-DFS algorithm for sequence diagram to generate concurrent test scenarios
Major  factors  contributing  to  generate  interleaving  concurrent  test  scenarios  are  total  order  of  messages,  interleaving  of  messages,  and  dependency  preservation  of  messages  inside  parallel  fragments.  
In  non-concurrentdesign,  a  test  scenario  is  represented  by  a  total  order  of  messages  as  a  sequence  of  nodes  (messages)  from  the start  node  to  the  end  node  traversed  by  DFS  or  BFS  algorithm. 
On  the  contrary,  concurrent  design  imposes  a condition  that  all  predecessor  nodes  of CFparEnd node  to  be  visited  inside  parallel  fragment  before  processing the CFparEnd node,  and  they  should  maintain  total  order  of  messages  in  each  interaction  operand. 
Therefore,standard DFS technique needs a modification. 
In a traditional CFG, the flexibility of choosing any message inside parallel fragments is not available because each interaction operand is represented as a sequence of messages from the start of operand to the end of the operand. 
Due to this, traversal algorithms, like DFS, generate a path without interleaving of messages. 
Consequently, a rich form of CFG is required such as MCFG, where additional message-interleaving edges are introduced. 
The message-interleaving edges help to traverse from any node to any other nodeinside parallel fragments. 
An MCFG facilitates generating interleaving of messages inside parallel fragments. 
With MCFG, there is a possibility of re-visiting the same node more than once in the DFS traversal process. 
This may lead generating unrealistic concurrent test paths. 
Therefore, a modified version of DFS algorithm with dependencypreserving  capability  is  necessary.  
In  short,  an  MCFG  and  modified  version  of  DFS  algorithm  are  required  togenerate interleaving and realistic concurrent test paths.

MCFG graph facilitates traversing of interleaving message sequences. 
In order to generate complete concurrenttest paths, a traversal algorithm should traverse an MCFG by preserving the total order in each interaction operand.
It should also ensure to traverse a single side of a conditional flow in the selection interaction fragments such as alt and loop.  
With above  restrictions,  we present  a modified  DFS  algorithm, which  we  call a dependency  preserving depth first search algorithm. 

An MCFG is a rich form of control flow graph where nodes and edges have some extra information useful for testscenario generation algorithms.
Definition: A message  control  flow  graphis  a  tuple G(V, E, n0, nf),  where V−a  set  of  nodes, E−a  set  ofedges, n0∈V −the initial node of graph, and nf ∈ V−the final node of graph.
In MCFG, a node n ∈ V is represented as a tuple(l, nt),l is a label associated with node and nt is a type of node, and an edge e ∈ E is represented as a tuple(v1, v2, g, et); v1, v2∈V, g is a guard associated with the edge, and et is a type of the edge. 
Nodes are broadly classified into two types:message nodes(MN) and control nodes(CN), i.e.,V= (MN∪CN). 
Message nodes are associated with each message, and control nodes associated with each fragment and  combined fragment of the sequence  diagram. 
Edges are classified as order-specification edges and message-interleaving edges. 
Message interleaving edges are represented as dotted lines and order-specification edges are represented as dark lines in MCFG diagrams. 
Message-interleaving edges represent virtual edges addedin the construction of MCFG for exploring message interleavings inside parallel fragments.

An Example of MCFG for the bank transaction sequence diagram is shown in the file BankTransaction2_MCFG.pdf.
