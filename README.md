# Slack3D [![Gitter Chat][gitter-badge]][gitter-link] [![Maven central][maven-badge]][maven-link]

[gitter-badge]: https://badges.gitter.im/Join%20Chat.svg

[gitter-link]: https://gitter.im/SwayDB-chat/Lobby

[maven-badge]: https://img.shields.io/maven-central/v/com.github.simerplaha/slack3d_2.13.svg

[maven-link]: https://search.maven.org/search?q=g:com.github.simerplaha%20AND%20a:slack3d_2.13

Simple 3D graphics engine.

![Bunny.gif](docs/bunny.gif)

# Features

- Phong lighting
- Fly style camera - Freely move around a 3D scene
- Text rendering - English characters and numbers.
- Out of the box Shapes - Vector, Box, Circle, Cone, Cylinder, HeightField, Plane, Point, Pyramid, Sphere, Tetrahedron,
  Triangle, Line & Ray
- OpenGL via LWJGL3
- User input to transform and rotate shapes.
- Basic linear algebra library
- Immutable API

## Key controls

- Space - Pauses current animation
- Enter - Return to initial camera view
- W, A, S, D - Move along the 3D axis
- Mouse drag - Changes camera view position

## Setup

```scala
libraryDependencies += "com.github.simerplaha" %% "slack3d" % "0.1.0"
```

## NOTE

**VM Option**: Allow LWJGL to run on main thread with `-XstartOnFirstThread`. If you are using IntelliJ set the flag in
the "Build and run" window.

![img.png](docs/intellij_vm_option.png)

## Rendering

Slack3D can be thought of a collection instance where your code provide shapes to render for each frame.

Render a sphere of radius `0.5` of colour `Purple`.

```scala
Slack3D("A sphere") foreach {
  state => //State of current render
    //shapes to render
    Seq(Sphere(radius = 0.5, colour = Colour.Purple))
}
```

![img.png](docs/purple_sphere.png)

## Rotation

Apply Y axis rotation to the box.

```scala
Slack3D("Rotating Box") foreach {
  state =>
    //same code as above but with added rotation at Y axis
    val box = Box(Colour.Red).rotateY(state.getTime() * 30)
    Seq(box)
}
```

![Cube_rotate.gif](docs/cube_rotate.gif)

Rotation can be applied to all axis. See APIs

![rotation_api.png](docs/rotation_api.png)

## Lines/Vectors

Create 2 vectors where the third vector is a cross product.

```scala
Slack3D("My rotation Box") foreach {
  state =>
    //vector1
    val vector1 = Vector3(0.5, -0.5, 0)
    //vector2
    val vector2 = Vector3(0.5, 0.5, 0)
    //cross product
    val cross = vector1 cross vector2

    Seq(
      Line(vector1, Colour.Red),
      Line(vector2, Colour.Yellow),
      Line(cross, Colour.Green)
    )
}
```

![img.png](docs/cross_product_vectors.png)

All `Lines` and `Points` will render a text displaying the position and length of that vector.


## Colours

TODO


## Custom shapes

TODO

## Custom lighting

TODO

## Moving shapes with user input

TODO

## Axis angle rotation

TODO

## `Meshable` type

TODO

## Camera setting

TODO

## Linear algebra

TODO
