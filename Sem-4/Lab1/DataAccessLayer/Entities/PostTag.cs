﻿// ***********************************************************************
// Assembly         : DataAccessLayer
// Author           : Slava
// Created          : 12-03-2022
//
// Last Modified By : Slava
// Last Modified On : 12-11-2022
// ***********************************************************************
// <copyright file="PostTag.cs" company="DataAccessLayer">
//     Copyright (c) . All rights reserved.
// </copyright>
// <summary></summary>
// ***********************************************************************
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DataAccessLayer.Entities;

/// <summary>
/// Class PostTag.
/// </summary>
public class PostTag
{
    /// <summary>
    /// Gets or sets the post identifier.
    /// </summary>
    /// <value>The post identifier.</value>
    [Key,Column(Order = 0)]
    public int PostId { get; set; }
    /// <summary>
    /// Gets or sets the tag identifier.
    /// </summary>
    /// <value>The tag identifier.</value>
    [Key,Column(Order = 1)]

    public int TagId { get; set; }

    /// <summary>
    /// Gets or sets the post.
    /// </summary>
    /// <value>The post.</value>
    public Post? Post { get; set; }
    /// <summary>
    /// Gets or sets the tag.
    /// </summary>
    /// <value>The tag.</value>
    public Tag? Tag { get; set; }
}
