  
## OO_Unit1总结

OO的第一单元作业告一段落，这周是总结而不是码代码，甚至心中有点落空感。OO课给我的一周构建了一个完整的循环，从周二的作业发布到接下来几天的思考和构建程序，再到面向中测进行一部分的bug修复，最后到互测的对他人代码的分析和思考，我觉得每一步我都有一定的提升，因此在这里做一个总结，使自己在之后的学习中能更好地吸取这次的经验和教训。

### 第一次作业

#### 拿到作业的思路

这次的作业总体上还是以一个面向过程的思路去完成的，心里没有对象的概念，面对给出的多项式，不清楚其中哪些部分可以抽象成一个对象。

因此在拿到作业的第一时间，我对指导书的关注更多在于一些细节--项与项之间是通过+-连接的--而没有对项的抽象。尽管我意识到了它们的存在，我对它们的理解却不是一个封装完好的，结构完整的对象。此时我更倾向于它们的内部细节是对我完全暴露的，这种思想在我第一次作业中是暴露得非常淋漓尽致的。

#### 代码的分析

| Project Name | Package Name      | Type Name | MethodName | LOC  | CC   | PC   |
| ------------ | ----------------- | --------- | ---------- | ---- | ---- | ---- |
| oo_u1hw1     | (default package) | MainClass | initTerms  | 15   | 1    | 0    |
| oo_u1hw1     | (default package) | MainClass | initPoly   | 18   | 4    | 1    |
| oo_u1hw1     | (default package) | MainClass | difPoly    | 10   | 3    | 1    |
| oo_u1hw1     | (default package) | MainClass | printPoly  | 54   | 14   | 1    |
| oo_u1hw1     | (default package) | MainClass | main       | 5    | 1    | 1    |

可以看到UML类图中只含有MainClass一个类，其中涉及了读入数据，数据处理，求导和打印，包揽了全部工作。而复杂度方面，printPoly属于重灾区，由于没有类的抽象，所有的数据打印细节都需要在这一个函数中进行考虑，这直接导致了代码行数难以控制和难以阅读，给后面的作业带来了麻烦。

#### 反思 

因为是面向过程的思路，所以代码的主体分为了三个部分：第一，初始化多项式，这个多项式是以一个项的幂次为key，项的系数为value的hashmap。第二，对这个多项式求导。第三，打印出求导的多项式。

这个过程中我基本算是一main到底，从读入，到把读入处理为一个一个项，再到把项的数组处理成多项式，到多项式的求导，到最后的打印。我都没有把每个项当作一个对象，一个具有自己方法和字段的对象，而是把这些项当作一些充分暴露给程序的数据，通过外在的方法去处理这些数据，最后输出的。

这样的方式在第一次看来没有问题，甚至看起来很简洁，因为第一次作业不仅结构简单，每一项只有系数和指数两个值，而且不涉及错误数据的输入处理，对代码的鲁棒性要求非常低。

所以这个方式有两个主要的问题：一是当项的结构变得更加复杂的时候，无法进行拓展，因为各个方法的逻辑都是基于每一项都是常数×幂函数的形式，如果这个形式发生变化，就意味着我整个程序的所有方法都要随之重写，大部分的代码不会被继承到下一次。二是代码基本不具有鲁棒性，我在输入的处理上用了许多在正确情况下才能使用的对输入的不可逆操作，这直接导致了代码在这些操作过程中有可能会将错误输入变为正确的，容易误认敌军为友军。

这些问题使得我在接下来的作业中走的稍微有点艰难。

### 第二次作业

#### 拿到作业的思路

由于OO连续性较强，我仍然沉浸在第一周的想法时，第二次作业已经悄然来袭。因此我在思考作业时仍然没有抛弃第一次的思路，仍然在想能不能在main里对所有数据进行处理。

然而在动手之前，我先去看了看讨论区的帖子，其中有一位提到了表达式树和层次化结构，对我大有启发，我也最终决定向这个方向靠拢，然而在靠拢的过程中，由于我的思维仍然处于过渡阶段，尚存有第一次的一些想法，导致我这次的转变也只是一个半成品。

#### 代码的分析

| Project Name | Package Name      | Type Name     | MethodName       | LOC  | CC   | PC   |
| ------------ | ----------------- | ------------- | ---------------- | ---- | ---- | ---- |
| oo_u1hw2     | (default package) | Constant      | Constant         | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Constant      | getData          | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Constant      | setData          | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Constant      | derivation       | 5    | 1    | 0    |
| oo_u1hw2     | (default package) | Constant      | print            | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Constant      | simplify         | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Constant      | canPrint         | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Cos           | Cos              | 3    | 1    | 2    |
| oo_u1hw2     | (default package) | Cos           | derivation       | 7    | 1    | 0    |
| oo_u1hw2     | (default package) | Cos           | print            | 8    | 3    | 0    |
| oo_u1hw2     | (default package) | Expression    | Expression       | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Expression    | Expression       | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Expression    | getTerms         | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Expression    | addTerm          | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Expression    | canTriSimp       | 12   | 4    | 0    |
| oo_u1hw2     | (default package) | Expression    | triSimplify      | 19   | 5    | 0    |
| oo_u1hw2     | (default package) | Expression    | ijTriSimplify    | 16   | 2    | 5    |
| oo_u1hw2     | (default package) | Expression    | derivation       | 9    | 2    | 0    |
| oo_u1hw2     | (default package) | Expression    | print            | 30   | 9    | 0    |
| oo_u1hw2     | (default package) | Expression    | simplify         | 19   | 6    | 0    |
| oo_u1hw2     | (default package) | Expression    | canPrint         | 8    | 3    | 0    |
| oo_u1hw2     | (default package) | Factor        | derivation       | 0    | 1    | 0    |
| oo_u1hw2     | (default package) | Factor        | print            | 0    | 1    | 0    |
| oo_u1hw2     | (default package) | Factor        | simplify         | 0    | 1    | 0    |
| oo_u1hw2     | (default package) | Factor        | canPrint         | 0    | 1    | 0    |
| oo_u1hw2     | (default package) | FactorFactory | getFactor        | 35   | 8    | 1    |
| oo_u1hw2     | (default package) | MainClass     | inputPreProcess1 | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | MainClass     | formatJudge      | 10   | 1    | 1    |
| oo_u1hw2     | (default package) | MainClass     | inputPreProcess2 | 22   | 1    | 1    |
| oo_u1hw2     | (default package) | MainClass     | initFactors      | 9    | 2    | 1    |
| oo_u1hw2     | (default package) | MainClass     | initTerms        | 8    | 2    | 1    |
| oo_u1hw2     | (default package) | MainClass     | main             | 29   | 5    | 1    |
| oo_u1hw2     | (default package) | Power         | Power            | 3    | 1    | 2    |
| oo_u1hw2     | (default package) | Power         | derivation       | 6    | 1    | 0    |
| oo_u1hw2     | (default package) | Power         | print            | 12   | 4    | 0    |
| oo_u1hw2     | (default package) | Sin           | Sin              | 3    | 1    | 2    |
| oo_u1hw2     | (default package) | Sin           | derivation       | 7    | 1    | 0    |
| oo_u1hw2     | (default package) | Sin           | print            | 8    | 3    | 0    |
| oo_u1hw2     | (default package) | Term          | Term             | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Term          | Term             | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Term          | getFactors       | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Term          | isPositive       | 8    | 3    | 0    |
| oo_u1hw2     | (default package) | Term          | similarTo        | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Term          | triSimilarTo     | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Term          | getSize          | 16   | 6    | 0    |
| oo_u1hw2     | (default package) | Term          | derivation       | 11   | 2    | 0    |
| oo_u1hw2     | (default package) | Term          | print            | 36   | 9    | 0    |
| oo_u1hw2     | (default package) | Term          | simplify         | 26   | 6    | 0    |
| oo_u1hw2     | (default package) | Term          | canPrint         | 8    | 3    | 0    |
| oo_u1hw2     | (default package) | Variable      | Variable         | 4    | 1    | 2    |
| oo_u1hw2     | (default package) | Variable      | getIndex         | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Variable      | setIndex         | 3    | 1    | 1    |
| oo_u1hw2     | (default package) | Variable      | derivation       | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Variable      | print            | 2    | 1    | 0    |
| oo_u1hw2     | (default package) | Variable      | simplify         | 3    | 1    | 0    |
| oo_u1hw2     | (default package) | Variable      | canPrint         | 3    | 1    | 0    |

可以看到，至少在形式上，这次作业和上次作业几乎是完全不同。抽象出了表达式-项-因子三个层级，并且因子作为接口，是可以有多种实现方式的，这也一定程度上增强了它的可拓展性。另外初步尝试使用工厂模式来进行因子的构建，尽管可能只是形似，但也给我接下来的学习奠定了一些基础。

UML图上看到了一些改变，然而从Loc等几个指标出发我们仍然能看出这次和上次的一些神似。MainClass类仍然承担了过多的功能，对输入的预处理和WF判别都是在其中完成的，这导致了其中的逻辑有过多的耦合，在之后的第三次作业中我就经历了一个漫长而痛苦的给它们解耦的过程。而打印的过程中，尽管分出了表达式-项-因子三个层级，却仍然存在个别地方过于复杂的情况，这主要是因为我对项和因子在输出逻辑上没有分清，有一部分可以放在因子的部分也被我不必要地放在了项上，造成了两者之间需要频繁沟通数据，最后造成了冗长的结果。

#### 反思 

这次的作业我总体上是非常不满意的，因为是一个四不像的产物，既不是完全地和第一次一样面向过程，也没有像我所想要的[表达式-项-因子]这样一个完善的层级结构。在对对象的建模过程中的模糊不清是这次代码逻辑较为混乱的一个关键因素，这个模糊不清主要是源于：1. 我尚未完全适应面向对象的思维方式；2. 主要思路来自于讨论区，对各个层级的建模是先有这个概念而后将数据和方法强行套进去，这样的硬套就导致一部分功能的错位；3. 化简的加入导致我对各个层级的思路被打乱了，最后使得它们之间大量互通数据来完成这个化简工作。

但这次作业对我而言也有好的一面，总体来说是我向面向对象转变的一个重要转折点，尝试性地使用接口来管理一类实现特定功能的类，尝试性地使用工厂模式来管理这些类的创建。这些都是思维转变的探索和过程，也是我再往后继续发展面向对象思维的一次好的尝试。

### 第三次作业

#### 拿到作业的思路

这次在看到表达式可以作为因子，以及指导书中所提示的表达式树之后，我当即决定就使用表达式树作为这次的数据结构来进行代码的构建。这是对我第二次作业的遗憾的一种弥补性措施吧。

这次代偿性地将第二次存在于MainClass的许多功能抽象成了Parse类去完成，包括读入数据，WF的判别，数据转变为表达式树，这个过程都在Parse去完成。而这次的WF由于不再能使用大正则匹配整个表达式的做法（存在嵌套），换为了try-catch进行处理，一旦读入不是正确形式就会抛出异常，这种方式我第一次是在第一次互测中见到，真正使用的时候就发现了它的好处，可以让我更快速地定位到数据的错误点。在之后就是根据表达式树这种特殊的二叉树来进行求导和打印。

#### 代码的分析

| Project Name | Package Name      | Type Name  | MethodName    | LOC  | CC   | PC   |
| ------------ | ----------------- | ---------- | ------------- | ---- | ---- | ---- |
| oo_u1hw3     | (default package) | Add        | Derivate      | 3    | 1    | 2    |
| oo_u1hw3     | (default package) | Add        | toString      | 12   | 3    | 2    |
| oo_u1hw3     | (default package) | Add        | toNestString  | 3    | 1    | 3    |
| oo_u1hw3     | (default package) | Const      | Const         | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Const      | getIndex      | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Const      | setIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Const      | multIndex     | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Const      | addIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Const      | Derivate      | 3    | 1    | 2    |
| oo_u1hw3     | (default package) | Const      | toString      | 3    | 1    | 2    |
| oo_u1hw3     | (default package) | Const      | toNestString  | 3    | 1    | 3    |
| oo_u1hw3     | (default package) | Cos        | Cos           | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Cos        | getIndex      | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Cos        | setIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Cos        | addIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Cos        | Derivate      | 7    | 1    | 2    |
| oo_u1hw3     | (default package) | Cos        | toString      | 9    | 3    | 2    |
| oo_u1hw3     | (default package) | Cos        | toNestString  | 21   | 5    | 3    |
| oo_u1hw3     | (default package) | Derivation | Derivate      | 0    | 1    | 2    |
| oo_u1hw3     | (default package) | Derivation | toString      | 0    | 1    | 2    |
| oo_u1hw3     | (default package) | Derivation | toNestString  | 0    | 1    | 3    |
| oo_u1hw3     | (default package) | Exp        | Exp           | 5    | 1    | 3    |
| oo_u1hw3     | (default package) | Exp        | getDerivation | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Exp        | derivate      | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Exp        | toString      | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Exp        | toNestString  | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Exp        | simp          | 12   | 4    | 0    |
| oo_u1hw3     | (default package) | Exp        | multSimp      | 42   | 12   | 0    |
| oo_u1hw3     | (default package) | Exp        | multUp        | 41   | 8    | 0    |
| oo_u1hw3     | (default package) | Exp        | intoMultList  | 9    | 2    | 2    |
| oo_u1hw3     | (default package) | Exp        | addSimp       | 28   | 7    | 0    |
| oo_u1hw3     | (default package) | Exp        | intoAddList   | 9    | 2    | 2    |
| oo_u1hw3     | (default package) | MainClass  | main          | 14   | 1    | 1    |
| oo_u1hw3     | (default package) | Mult       | Derivate      | 5    | 1    | 2    |
| oo_u1hw3     | (default package) | Mult       | toString      | 31   | 8    | 2    |
| oo_u1hw3     | (default package) | Mult       | toNestString  | 14   | 4    | 3    |
| oo_u1hw3     | (default package) | Nest       | Derivate      | 4    | 1    | 2    |
| oo_u1hw3     | (default package) | Nest       | toString      | 3    | 1    | 2    |
| oo_u1hw3     | (default package) | Nest       | toNestString  | 3    | 1    | 3    |
| oo_u1hw3     | (default package) | Parse      | Parse         | 9    | 2    | 1    |
| oo_u1hw3     | (default package) | Parse      | skipBlank     | 5    | 2    | 0    |
| oo_u1hw3     | (default package) | Parse      | getExp        | 37   | 6    | 0    |
| oo_u1hw3     | (default package) | Parse      | getTerm       | 24   | 4    | 0    |
| oo_u1hw3     | (default package) | Parse      | getFactor     | 25   | 1    | 0    |
| oo_u1hw3     | (default package) | Parse      | getPower      | 25   | 6    | 0    |
| oo_u1hw3     | (default package) | Parse      | getSin        | 34   | 7    | 0    |
| oo_u1hw3     | (default package) | Parse      | getCos        | 34   | 7    | 0    |
| oo_u1hw3     | (default package) | Parse      | getConst      | 9    | 2    | 0    |
| oo_u1hw3     | (default package) | Parse      | getExpF       | 22   | 6    | 0    |
| oo_u1hw3     | (default package) | Power      | Power         | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Power      | getIndex      | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Power      | setIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Power      | addIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Power      | Derivate      | 5    | 1    | 2    |
| oo_u1hw3     | (default package) | Power      | toString      | 9    | 3    | 2    |
| oo_u1hw3     | (default package) | Power      | toNestString  | 6    | 2    | 3    |
| oo_u1hw3     | (default package) | Sin        | Sin           | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Sin        | getIndex      | 3    | 1    | 0    |
| oo_u1hw3     | (default package) | Sin        | setIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Sin        | addIndex      | 3    | 1    | 1    |
| oo_u1hw3     | (default package) | Sin        | Derivate      | 7    | 1    | 2    |
| oo_u1hw3     | (default package) | Sin        | toString      | 9    | 3    | 2    |
| oo_u1hw3     | (default package) | Sin        | toNestString  | 21   | 5    | 3    |

可以看到这次的总体结构呈现出：表达式-因子的结构，这次抛弃了项这一中间层级，现在想想，不是一种好的做法，这在化简过程中极大地复杂了我表达式的处理，如果仍然有项这一级，可能会好很多。

这次之所以抛弃了项这一级，是因为我将+-*这些运算也归为了因子，虽然这是表达式树的要求，但这也极大地方便了求导规则的制定，不需要在每个因子内部去思考和其他项在一起时如何求导，而是在各个运算符里去考虑这些。其实这和项的存在并不冲突，但是我当时可能只拘泥于运算符独立出来作为一个类，而忽略了项这一级的重要性和存在的可能性，这是我思维的一个漏洞。

而在方法的几个指标里就很好地体现出了这次没有项的劣势。尽管在Parse里有getTerm这个方法，但它的存在只是作为Parse内部的一种简化处理方式，这就导致了Exp类需要接收过多的直接来自于各个因子的信息，方法长度陡然上升，例如multSimp和addSimp两个方法，由于是直接和因子交互，需要考虑的逻辑细节众多，长度不可避免地增加，后面许多bug也是出现在这个地方。各个因子的toString也是受害者，由于没有项这一级的存在，打印运算符时必须了解运算符连接的两个因子的细节，所以toString有了lexp和rexp两个参数，来告知这些细节。Mult的toStirng膨胀到三十多行也是项这一级缺少的结果，不必要的逻辑细节被暴露给了Mult。

#### 反思 

这次是有一定好的改变：即表达式树使我从更抽象也更面向对象的角度去观察表达式这个对象，把他拆解成了非常基本的几个元素，使我在处理这些元素时感觉非常轻松，因为其逻辑清晰且简单。但是这次也存在一个不足：即步子跨的太大，表达式被分解的过分彻底，使得最后表达式成为一个管理各个元素的总揽，缺乏一个必要的中间层。

这次也在其他维度给我带来了提升，一个就是java的异常机制，初步运用了异常机制去处理WF的问题；第二就是java的String的+的问题，本来在toString中大量用+进行字符串的拼接，后来在测试的过程中发现处理速度非常慢，了解到了其工作原理后改用了StringBuilder进行这部分的处理。

### 评测与互测

#### BUGs

这几次的评测过程中，第一次在强测和互测中都没有被发现BUG，而第二次第三次都是在互测中被发现了一个BUG~~（并且被其他几个人毒打）~~。其中第二次是由于读入过程中对WF的判断存在逻辑漏洞，第三次是化简过程中的括号问题。这两次都是在一些复杂的逻辑中存在不容易观察到的漏洞，这些漏洞在我的评测机的随机数据中不容易被爆破，而我自己构造的样例也不会覆盖这些~~（我如果能写出这样的样例就不会出这种错了orz）~~。

#### 评测机

构建评测机这个想法来源于指导书给出的验证程序答案正确性的方法，既然课程组可以采用这样的方法进行测试，那我为什么不能也用这个方法对我的程序进行测试呢。抱着这样的想法，我选择了自己搭建评测机，凭借着讨论区所提供的一些实用工具，成功构建出了一个由Python生成数据，Python对数据求导并代值运算，脚本比对我的结果与正确结果的评测机。

这个评测机伴随了我三次作业，测出了一些bug，但也在测试的过程中发现了它的不足。它最大的不足就是随机的数据生成很难测试到极端情况，需要我手动构造样例去进行压力测试。这个缺点是源自于我是用一个很大的正则表达式去生成数据，这个里面没有任何可调参数。最后随机性虽然很足，但是却成为一个完全不可控的~~无情的（划去）~~测试机器。这也影响了我在互测中的测试策略。

#### 互测策略

从评测机的缺点中我得到了一个结论，即便给出许多组随机数据进行测试，仍然存在许多测不到的点，这就需要我手动去构造许多边界数据去进行测试。因此我的互测策略是，1. 使用我的评测机去测试他们的程序，如果出错，大概率是其程序有很多错误或一个很明显的错误，直接提交错误点即可；2. 使用手动构造的数据进行测试，这些主要测试是一些极端形况其程序是否考虑；3. 观察其代码，以其找到逻辑漏洞。

在遵循这三步策略的情况下，我在三次互测中都成功找出了房间其他人的一个或多个bug 。

### 总结

第一单元的作业告一段落了，但是它带给我的影响却还没有停止。在这篇的反思过程中我理顺了自己构建代码的思路，同时发现了自己的思维漏洞，即对对象的封装总是不完备，容易将许多对象耦合在一起，使得它们之间的逻辑复杂、难以理解且难以处理，这是我需要在之后改善的。其次就是评测机的构建给我一个新的理解问题的思路，从评测的角度去理解，有时候更容易理清思路。

这次总结没有倾向于关注代码中的技术思路和细节，而更多地是我自己在构建代码时的想法，这主要是因为我尚没有特别掌握面向对象的思想，如果过多地关注技术细节，可能会使我更偏居一隅，无法从整体的角度去看待这几次作业的影响，因此在这里展示自己思考的心路历程，可能对我改善自己的思维方式更有裨益。



